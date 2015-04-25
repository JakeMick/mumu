import unittest
import numpy as np
from keras.models import Sequential
from keras.layers.core import Dense, Dropout, Activation
from keras.layers.normalization import BatchNormalization
from keras.optimizers import SGD
from ..serialize_model import SerializedKeras


def fancy_nn(in_dim, out_dim):
    composition = [
        Dense(in_dim, 100, init='glorot_normal', activation='relu'),
        Activation('relu'),
        BatchNormalization(100),
        Dropout(0.5),
        Dense(100, 100, init='glorot_normal'),
        Activation('relu'),
        BatchNormalization(100),
        Dropout(0.5),
        Dense(100, out_dim, init='glorot_normal'),
        Activation('sigmoid')
    ]
    model = Sequential()
    for layer in composition:
        model.add(layer)
    return model


def fancy_nn_reg(in_dim, out_dim):
    composition = [
        Dense(in_dim, 100, init='glorot_normal'),
        Activation('relu'),
        BatchNormalization(100),
        Dropout(0.5),
        Dense(100, 100, init='glorot_normal'),
        Activation('relu'),
        BatchNormalization(100),
        Dropout(0.5),
        Dense(100, out_dim, init='glorot_normal'),
        Activation('linear')
    ]
    model = Sequential()
    for layer in composition:
        model.add(layer)
    return model


class TestClazz(unittest.TestCase):

    def setUp(self):
        self.nn = fancy_nn(10, 10)
        self.nn.compile(SGD(), 'mse')

    def test_serial_deserial(self):
        serer = SerializedKeras()
        sered = serer.serialize(self.nn)
        deserer = SerializedKeras()
        nn_de = deserer.deserialize(sered)
        nn_de.compile(SGD(), 'mse')
        x = np.random.random((100, 10))
        assert(np.allclose(self.nn.predict_proba(x),
                           nn_de.predict_proba(x)))

if __name__ == '__main__':
    unittest.main()
