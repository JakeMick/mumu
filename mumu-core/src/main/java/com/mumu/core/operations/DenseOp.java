package com.mumu.core.operations;

import com.mumu.core.activation.Activation;
import com.mumu.core.operations.iface.GraphOperation;
import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrix;
import no.uib.cipr.matrix.Vector;


public class DenseOp implements GraphOperation {
    private final Activation activation;
    private       Matrix     bias;
    private final Matrix     weight;
    private final Matrix     output;
    private final int        batch;
    private final int        parent;
    private final int        inShape;
    private final int        outShape;

    public DenseOp(Activation activation, Vector biasMu, Matrix weightMu, int batch, int parent) {
        this.batch = batch;
        this.inShape = weightMu.numRows();
        this.outShape = weightMu.numColumns();
        this.activation = activation;
        getDenseVectorRep(batch, biasMu);
        this.weight = weightMu;
        this.output = new DenseMatrix(batch, outShape);
        this.parent = parent;
        assert bias != null;
        assert outShape == bias.numColumns();
    }

    private void getDenseVectorRep(int batch, Vector biasMu) {
        bias = new DenseMatrix(batch, biasMu.size());
        for (int i = 0; i < batch; i++)
            for (int j = 0; j < outShape; j++)
                bias.set(i, j, biasMu.get(j));
    }


    public Matrix batchOp(Matrix input) {
        input.mult(weight, output);
        output.add(bias);
        return output;
    }

    public int getParent() {
        return parent;
    }

    public int getIn() {
        return inShape;
    }

    public int getOut() {
        return outShape;
    }
}
