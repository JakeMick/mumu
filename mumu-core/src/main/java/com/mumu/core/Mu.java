package com.mumu.core;

import com.mumu.core.activation.Activation;
import com.mumu.core.graph.GraphicalNeuralNetwork;
import com.mumu.core.operations.*;
import com.mumu.core.operations.iface.GraphOperation;
import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.DenseVector;
import no.uib.cipr.matrix.Matrix;
import no.uib.cipr.matrix.Vector;

public class Mu {
    private GraphicalNeuralNetwork graphComputation = new GraphicalNeuralNetwork();

    private boolean initialized = false;

    public void build() {
        initialized = true;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void addActivation(MumuGen.ActivationNode activationNode) throws Exception {
        MumuGen.Activation activation = activationNode.getActivation();
        MumuGen.DenseNode denseNode = MumuGen.DenseNode.newBuilder()
                .setActivation(activationNode.getActivation())
                .build();
        addDense(denseNode);

    }

    public void addDropout(MumuGen.DropoutNode dropoutNode) {
        DropoutOp op = new DropoutOp(dropoutNode.getProbability());
        graphComputation.insert(op);
    }

    public void addDense(MumuGen.DenseNode denseNode) {
        MumuGen.Array bias = denseNode.getBias();
        Vector biasV = getVector(bias);
        MumuGen.Array weight = denseNode.getWeight();
        Matrix weightV = getMatrix(weight);
        GraphOperation op = new DenseOp(new Activation(), biasV, weightV, 10, denseNode.getParent());
        graphComputation.insert(op);
    }

    private Matrix getMatrix(MumuGen.Array weight) {
        DenseMatrix out = new DenseMatrix(weight.getDim(0), weight.getDim(1));
        return out;
    }

    private Vector getVector(MumuGen.Array bias) {
        DenseVector out = new DenseVector(bias.getDim(0));
        return out;
    }

    private void addActivation(int number, int parent) throws Exception {
        if (number == MumuGen.Activation.hard_sigmoid_VALUE) {
            HardSigmoidOp op = new HardSigmoidOp();
            graphComputation.insert(op);
        } else if (number == MumuGen.Activation.linear_VALUE) {
            DummyOp op = new DummyOp();
            graphComputation.insert(op);
        } else if (number == MumuGen.Activation.relu_VALUE) {
            ReluOp op = new ReluOp();
            graphComputation.insert(op);
        } else if (number == MumuGen.Activation.sigmoid_VALUE) {
            SigmoidOp op = new SigmoidOp();
            graphComputation.insert(op);
        } else if (number == MumuGen.Activation.softplus_VALUE) {
            SoftplusOp op = new SoftplusOp();
            graphComputation.insert(op);
        } else if (number == MumuGen.Activation.tanh_VALUE) {
            TanhOp op = new TanhOp();
            graphComputation.insert(op);
        } else {
            throw new Exception("Not implemented ");
        }
    }

    public void addNormalization(MumuGen.NormalizationNode normalizationNode) {
    }
    
    public void predict(DenseMatrix input) {
        
        
    }
}
