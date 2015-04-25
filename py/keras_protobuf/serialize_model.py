from keras.models import Sequential
from keras.layers.core import Dense, Dropout, Activation
from keras.layers.normalization import BatchNormalization
from .gen.proto import mumu_pb2 as mu
import numpy as np
import operator


class SerializedKeras():

    def __init__(self):
        self._activationz = dict(mu.Activation.items())
        self._deactivationz = {}
        for k, v in self._activationz.items():
            self._deactivationz[v] = k.lower()

    def deserialize(self, serialized_model):
        self._deserialized_network = []
        for ser_layer in serialized_model.layers:
            layer_name = ser_layer.WhichOneof('Layer')
            if layer_name == 'denseNode':
                self._deserialize_dense(ser_layer)
            elif layer_name == 'activationNode':
                self._deserialize_activation(ser_layer)
            elif layer_name == 'normalizationNode':
                self._deserialize_batch_normalization(ser_layer)
            elif layer_name == 'dropoutNode':
                self._deserialize_dropout(ser_layer)
            else:
                raise Exception("Not implemented: {}".format(str(ser_layer)))
        network = sorted(dict(self._deserialized_network).items(),
                         key=operator.itemgetter(0))
        model = Sequential()
        for id, layer in network:
            model.add(layer)
        return model

    def serialize(self, model):
        self._serialized_network = mu.Network()
        for (ind, keras_layer) in enumerate(model.layers):
            self._serialized_layer = self._serialized_network.layers.add()
            if isinstance(keras_layer, Dense):
                self._serialize_dense(keras_layer, ind)
            elif isinstance(keras_layer, Activation):
                self._serialize_activation(keras_layer, ind)
            elif isinstance(keras_layer, BatchNormalization):
                self._serialize_batch_normalization(keras_layer, ind)
            elif isinstance(keras_layer, Dropout):
                self._serialize_dropout(keras_layer, ind)
            else:
                raise Exception("Not implemented: {}".format(str(keras_layer)))
        return self._serialized_network

    def _serialize_dropout(self, keras_layer, parent_id):
        self._serialized_layer.dropoutNode.parent = parent_id
        proba = keras_layer.p
        self._serialized_layer.dropoutNode.probability = proba

    def _deserialize_dropout(self, ser_layer):
        proba = ser_layer.dropoutNode.probability
        layer = Dropout(proba)
        self._deserialized_network.append((ser_layer.dropoutNode.parent,
                                           layer))

    def _serialize_batch_normalization(self, keras_layer, parent_id):
        self._serialized_layer.normalizationNode.parent = parent_id
        beta = keras_layer.beta.get_value()
        self._serialize_array(beta,
                              self._serialized_layer.normalizationNode.beta)
        epsilon = keras_layer.epsilon
        self._serialized_layer.normalizationNode.epsilon = epsilon
        gamma = keras_layer.gamma.get_value()
        self._serialize_array(gamma,
                              self._serialized_layer.normalizationNode.gamma)

    def _deserialize_batch_normalization(self, ser_layer):
        in_shape = ser_layer.normalizationNode.beta.dim[0]
        epsilon = ser_layer.normalizationNode.epsilon
        layer = BatchNormalization(in_shape, epsilon=epsilon)

        beta = self._deserialize_array(ser_layer.normalizationNode.beta)
        layer.beta.set_value(beta)

        gamma = self._deserialize_array(ser_layer.normalizationNode.gamma)
        layer.gamma.set_value(gamma)
        self._deserialized_network.append((ser_layer.normalizationNode.parent,
                                           layer))

    def _serialize_dense(self, keras_layer, parent_id):
        self._serialized_layer.denseNode.parent = parent_id
        weight = keras_layer.W.get_value()
        self._serialize_array(weight, self._serialized_layer.denseNode.weight)
        bias = keras_layer.b.get_value()
        self._serialize_array(bias, self._serialized_layer.denseNode.bias)
        self._serialized_layer.denseNode.activation \
            = self._activationz[keras_layer.activation.func_name]

    def _deserialize_dense(self, ser_layer):
        in_shape, out_shape = ser_layer.denseNode.weight.dim
        activation = self._deactivationz[ser_layer.denseNode.activation]
        layer = Dense(in_shape, out_shape, activation=activation)
        weights = self._deserialize_array(ser_layer.denseNode.weight)
        layer.W.set_value(weights)
        biases = self._deserialize_array(ser_layer.denseNode.bias)
        layer.b.set_value(biases)
        self._deserialized_network.append((ser_layer.denseNode.parent, layer))

    def _serialize_activation(self, keras_layer, parent_id):
        self._serialized_layer.activationNode.parent = parent_id
        act_func = keras_layer.activation.func_name
        try:
            self._serialized_layer.activationNode.activation \
                = self._activationz[act_func]
        except:
            raise Exception("Not implemented: {}".format(str(act_func)))

    def _deserialize_activation(self, ser_layer):
        act_func = self._deactivationz[ser_layer.activationNode.activation]
        try:
            layer = Activation(act_func)
        except:
            raise Exception("Not implemented: {}".format(str(act_func)))
        self._deserialized_network.append((ser_layer.activationNode.parent,
                                           layer))

    def _serialize_array(self, arr, serial):
        for d in arr.shape:
            serial.dim.append(d)
        for value in np.nditer(arr, order='C'):
            serial.content.append(value.item())

    def _deserialize_array(self, arr):
        out = np.array(arr.content, dtype='float32', order='C')
        return out.reshape(arr.dim)
