package com.mumu.core.operations;

import com.mumu.core.operations.iface.GraphOperation;
import no.uib.cipr.matrix.Matrix;

public class HardSigmoidOp implements GraphOperation {
    private final float slope = (float) 0.2;
    private final float shift = (float) 0.5;

    public Matrix batchOp(Matrix input) {
        input.scale(slope);
        for (int i = 0; i < input.numRows(); i++) {
            for (int j = 0; j < input.numColumns(); i++) {
                input.set(i, j, input.get(i, j) + shift);
            }
        }
        return input;
    }

    public int getParent() {
        return 0;
    }

    public int getIn() {
        return 0;
    }

    public int getOut() {
        return 0;
    }
}
