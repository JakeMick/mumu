package com.mumu.core.operations;

import no.uib.cipr.matrix.*;

public class Utility {
    public Matrix getSquare(double val) {
        DenseMatrix data = new DenseMatrix(10, 10);
        for (MatrixEntry i : data) {
            i.set(val);
        }
        return data;
    }

    public DenseVector getVec(double val) {
        DenseVector data = new DenseVector(10);
        for (VectorEntry i : data) {
            i.set(val);
        }
        return data;
        
    }

    public boolean allClose(Matrix data, double target) {
        boolean allGood = true;
        for (MatrixEntry i : data) {
            double value = i.get();
            allGood &= (Math.abs(target - value) < 0.000001);
        }
        return allGood;
    }
}
