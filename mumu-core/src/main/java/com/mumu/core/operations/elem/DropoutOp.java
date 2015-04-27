package com.mumu.core.operations.elem;

import com.mumu.core.operations.base.ElementWise;
import no.uib.cipr.matrix.Matrix;

public class DropoutOp extends ElementWise {
    private final double probability;

    public DropoutOp(double probabilityDropout) {
        this.probability = 1 - probabilityDropout;
    }

    @Override
    public Matrix batchOp(Matrix input) {
        return input.scale(this.probability);
    }
}
