package com.mumu.core;

import com.mumu.core.graph.GraphicalNeuralNetwork;
import com.mumu.core.operations.dense.DenseTransformationOp;
import com.mumu.core.operations.dense.NormalizationOp;
import com.mumu.core.operations.elem.*;
import com.mumu.core.operations.base.ElementWise;
import com.mumu.core.operations.base.GraphOp;
import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.DenseVector;
import no.uib.cipr.matrix.Matrix;
import no.uib.cipr.matrix.Vector;

import java.util.Iterator;
import java.util.Set;

class Mu {
    private final GraphicalNeuralNetwork graphComputation = new GraphicalNeuralNetwork();

    private boolean initialized = false;

    public Set<GraphOp> build() throws Exception {
        initialized = true;
        return graphComputation.build();
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void addActivation(MumuGen.ActivationNode activationNode, int nodeId, int parentId) throws Exception {
        ElementWise op = getActivation(activationNode.getActivation());
        setIds(op, nodeId, parentId);
        graphComputation.insert(op);
    }

    private GraphOp setIds(GraphOp operation, int nodeId, int parentId) {
        operation.setNodeId(nodeId);
        operation.setParentId(parentId);
        return operation;
    }

    public void addDropout(MumuGen.DropoutNode dropoutNode, int nodeId, int parentId) {
        DropoutOp op = new DropoutOp(dropoutNode.getProbability());
        setIds(op, nodeId, parentId);
        graphComputation.insert(op);
    }

    public void addDense(MumuGen.DenseNode denseNode, int nodeId, int parentId) throws Exception {
        MumuGen.Array bias = denseNode.getBias();
        Vector biasV = getDenseVector(bias);
        MumuGen.Array weight = denseNode.getWeight();
        Matrix weightV = getDenseTwoMatrix(weight);
        ElementWise activation = getActivation(denseNode.getActivation());
        GraphOp op = new DenseTransformationOp(weightV, biasV, activation);
        setIds(op, nodeId, parentId);
        graphComputation.insert(op);
    }

    private Matrix getDenseTwoMatrix(MumuGen.Array array) throws Exception {
        int dimCount = array.getDimCount();
        if (dimCount != 2) {
            throw new Exception("Trying to get a 2D matrix from the wrong dimensionality " + String.valueOf(dimCount));
        }
        int rows = array.getDim(0);
        int cols = array.getDim(1);
        DenseMatrix out = new DenseMatrix(rows, cols);
        Iterator<Float> content = array.getContentList().iterator();
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                out.set(i, j, content.next());
        return out;
    }

    private Vector getDenseVector(MumuGen.Array array) throws Exception {
        if (array.getDimCount() != 1) {
            throw new Exception("Trying to get a vector from the wrong shape");
        }
        int size = array.getDim(0);
        DenseVector vector = new DenseVector(size);
        Iterator<Float> content = array.getContentList().iterator();
        for (int i = 0; i < size; i++) {
            vector.set(i, content.next());
        }
        return vector;
    }

    private ElementWise getActivation(MumuGen.Activation activation) throws Exception {
        int activationId = activation.getNumber();
        if (activationId == MumuGen.Activation.hard_sigmoid_VALUE) {
            return new HardSigmoidOp();
        } else if (activationId == MumuGen.Activation.linear_VALUE) {
            return new DummyOp();
        } else if (activationId == MumuGen.Activation.relu_VALUE) {
            return new ReluOp();
        } else if (activationId == MumuGen.Activation.sigmoid_VALUE) {
            return new SigmoidOp();
        } else if (activationId == MumuGen.Activation.softplus_VALUE) {
            return new SoftplusOp();
        } else if (activationId == MumuGen.Activation.tanh_VALUE) {
            return new TanhOp();
        }
        throw new Exception("Activation not implemented " + String.valueOf(activationId));
    }

    public void addNormalization(MumuGen.NormalizationNode normalizationNode, int nodeId, int parentId) throws Exception {
        Vector beta = getDenseVector(normalizationNode.getBeta());
        Vector gamma = getDenseVector(normalizationNode.getGamma());
        double epsilon = normalizationNode.getEpsilon();
        NormalizationOp op = new NormalizationOp(beta, gamma, epsilon);
        setIds(op, nodeId, parentId);
        graphComputation.insert(op);
        
        
    }

}
