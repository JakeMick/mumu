pro:
	protoc proto/mumu.proto --python_out=py/keras_protobuf/gen/ --java_out=mumu-core/src/main/java

all: pro
	pro
