import unittest
import numpy as np
import pandas as pd
from keras.models import Sequential
from keras.layers.core import Dense, Dropout, Activation
from keras.layers.normalization import BatchNormalization
from keras.optimizers import SGD
from sklearn.datasets import make_circles
from ..serialize_model import SerializedKeras


def multilayer_linear_classifier(in_dim, out_dim):
    composition = [
        Dense(in_dim, 100, init='glorot_normal', activation='relu'),
        Activation('relu'),
        #BatchNormalization(100),
        Dropout(0.5),
        Dense(100, 100, init='glorot_normal'),
        Activation('relu'),
        #BatchNormalization(100),
        Dropout(0.5),
        Dense(100, out_dim, init='glorot_normal'),
        Activation('sigmoid')
    ]
    model = Sequential()
    for layer in composition:
        model.add(layer)
    return model


class TestSerialDeserial(unittest.TestCase):

    def setUp(self):
        X, y = make_circles(1000, random_state=123)
        self.nn = multilayer_linear_classifier(X.shape[1], 1)
        self.nn.compile(SGD(), 'binary_crossentropy')
        self.nn.fit(X[:800], y[:800])
        self.X_te, self.y_te = X[800:], y[800:]

    def test_serial_deserial(self):
        serializer = SerializedKeras()
        self.serialized_model = serializer.serialize(self.nn)
        deserializer = SerializedKeras()
        model = deserializer.deserialize(self.serialized_model)
        model.compile(SGD(), 'binary_crossentropy')
        assert(np.allclose(self.nn.predict_proba(self.X_te),
                           model.predict_proba(self.X_te)))

    def tearDown(self):
        with open('../temp/nn.bin', 'w') as handle:
            handle.write(self.serialized_model.SerializeToString())
        df = pd.DataFrame(self.X_te)
        df['y'] = self.y_te
        df.to_csv('../temp/data.csv', header=False, index=False)
        df = pd.DataFrame(self.nn.predict_proba(self.X_te))
        df.to_csv('../temp/pred.csv', header=False, index=False)


if __name__ == '__main__':
    unittest.main()
