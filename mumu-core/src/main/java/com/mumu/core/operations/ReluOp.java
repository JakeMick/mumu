package com.mumu.core.operations;

import com.mumu.core.operations.iface.GraphOperation;
import no.uib.cipr.matrix.Matrix;

public class ReluOp implements GraphOperation {
    public Matrix batchOp(Matrix input) {
        for (int i = 0; i < input.numRows(); i++) {
            for (int j = 0; i < input.numColumns(); j++) {
                input.set(i, j, Math.max(input.get(i, j), 0));
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
