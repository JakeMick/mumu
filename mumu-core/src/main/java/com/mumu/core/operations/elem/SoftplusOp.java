package com.mumu.core.operations.elem;

import com.mumu.core.operations.base.ElementWise;
import no.uib.cipr.matrix.Matrix;
import no.uib.cipr.matrix.MatrixEntry;

public class SoftplusOp extends ElementWise {
    @Override
    public Matrix batchOp(Matrix input) {
        for (MatrixEntry i : input)
            i.set(Math.log(1.0 + Math.exp(i.get())));
        return input;
    }
}
