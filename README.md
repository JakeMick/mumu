# mumu 

Mumu is a serialization toolkit for neural networks built with [keras][0].
It is being built concurrently with an efficient consumer in Java. The goal
is to make a portable abstraction of arbitrarily structured nets so that they
can be deployed with low-latency on commodity hardware. Eventually this might
expand to include a serial a subset of [torchnn][1].

Pull requests are welcome.

Dependencies
------------
Requires protobuf >= 2.6

(Optional) Generating Protobuf API
----------------------------------
make

Running Tests
-------------
nosetests

Install
-------
python setup.py install

[0]: https://github.com/fchollet/keras
[1]: https://github.com/torch/nn
