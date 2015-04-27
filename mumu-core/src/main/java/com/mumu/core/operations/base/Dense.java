package com.mumu.core.operations.base;

import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrix;
import no.uib.cipr.matrix.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Dense extends GraphOp {
    private final Logger logger = LoggerFactory.getLogger(Dense.class);
    protected Matrix output;
    protected int    batch;
    protected boolean initialized = false;

    protected void makeOut(int batch, int outShape) {
        output = new DenseMatrix(batch, outShape);
    }

    private void initializationCheck(Matrix input) {
        int nrows = input.numRows();
        if (!initialized) {
            logger.warn("The batch size was not specified. " +
                        "Assuming a default size of input rows " +
                        String.valueOf(nrows)
            );
            setBatch(nrows);
        }
        if (nrows != this.batch) {
            logger.warn("The batch size is different than submitted rows. " +
                        "Adjusting the submitted rows. " +
                        "This is a performance issues."
            );
            setBatch(nrows);
        }
    }

    public Matrix batchOp(Matrix input) {
        initializationCheck(input);
        Matrix result = apply(input);
        return result;
    }

    protected DenseMatrix makeVectorReplicate(int batch, Vector vector) {
        DenseMatrix replicated = new DenseMatrix(batch, vector.size());
        for (int i = 0; i < batch; i++)
            for (int j = 0; j < vector.size(); j++)
                replicated.set(i, j, vector.get(j));
        return replicated;
    }

    protected abstract Matrix apply(Matrix input);
}
