package com.mumu.core.operations.elem;

import com.mumu.core.operations.base.ElementWise;
import no.uib.cipr.matrix.Matrix;
import no.uib.cipr.matrix.MatrixEntry;

public class TanhOp extends ElementWise {

    @Override
    public Matrix batchOp(Matrix input) {
        for (MatrixEntry i : input)
            i.set(Math.tanh(i.get()));
        return input;
    }
}
