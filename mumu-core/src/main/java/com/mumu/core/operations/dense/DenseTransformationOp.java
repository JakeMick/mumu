package com.mumu.core.operations.dense;

import com.mumu.core.operations.base.Dense;
import com.mumu.core.operations.base.ElementWise;
import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrix;
import no.uib.cipr.matrix.Vector;


public class DenseTransformationOp extends Dense {
    private final ElementWise activation;
    private final int         inShape;
    private final int         outShape;
    private final Vector      bias;
    private final Matrix      weight;
    private       int         parent;
    private       DenseMatrix biasRep;
    private       DenseMatrix resultMatrix;

    public DenseTransformationOp(Matrix weightMu, Vector biasMu, ElementWise elementWise) {
        this.inShape = weightMu.numRows();
        this.outShape = weightMu.numColumns();
        this.activation = elementWise;
        this.bias = biasMu;
        this.weight = weightMu;
    }


    public void initialize() {
        biasRep = makeVectorReplicate(batch, bias);
        makeOut(batch, outShape);
        initialized = true;
    }

    public void setBatch(int batch) {
        this.batch = batch;
        initialize();
    }

    protected Matrix apply(Matrix input) {
        input.mult(weight, output);
        output.add(biasRep);
        activation.batchOp(output);
        return output;
    }

}
