# mumu 

Mumu is a Python glue for for serializing neural networks built in
[keras][0] to deploy in Java. The protocol buffer schema/comments contains
enough information for model.predict(). The schema doesn’t store information on
how to train the
model.

The goal of this project is to provide a simple way to deploy models built
with Keras in C++, ObjC, Java, or other languages. It would take a long time
to reimplement the sophistication of Theano in those languages.

In principle, it is possible to implement compatible protobuf providers
for [Mocha][1] (Julia), or [Torch][2] (Lua). Currently Keras only supports
Sequential models. In the future it might add support for Parallel and Concat.

Pull requests are welcome.

Dependencies
------------
Requires protobuf >= 2.6

(Optional) Generating Protobuf API
----------------------------------
protoc proto/mumu.proto --python_out=keras_protobuf/gen/

Running Tests
-------------
nosetests

Install
-------
python setup.py install

[0]: https://github.com/fchollet/keras
[1]: https://github.com/pluskid/Mocha.jl
[2]: https://github.com/torch/nn
