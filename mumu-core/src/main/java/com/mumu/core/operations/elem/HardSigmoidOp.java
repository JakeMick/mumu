package com.mumu.core.operations.elem;

import com.mumu.core.operations.base.ElementWise;
import no.uib.cipr.matrix.Matrix;
import no.uib.cipr.matrix.MatrixEntry;

public class HardSigmoidOp extends ElementWise {
    private final float slope = (float) 0.2;
    private final float shift = (float) 0.5;

    @Override
    public Matrix batchOp(Matrix input) {
        for (MatrixEntry i : input) {
            double val = i.get();
            if (val < -2.5) {
                i.set(0);
            } else if (val > 2.5) {
                i.set(1);
            } else {
                i.set(val * slope + shift);
            }
        }
        return input;
    }


}
