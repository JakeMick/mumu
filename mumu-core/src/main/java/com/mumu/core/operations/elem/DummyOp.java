package com.mumu.core.operations.elem;

import com.mumu.core.operations.base.ElementWise;
import no.uib.cipr.matrix.Matrix;

public class DummyOp extends ElementWise {
    @Override
    public Matrix batchOp(Matrix input) {
        return input;
    }
}
