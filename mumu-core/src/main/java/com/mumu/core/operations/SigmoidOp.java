package com.mumu.core.operations;

import com.mumu.core.operations.iface.GraphOperation;
import no.uib.cipr.matrix.Matrix;

public class SigmoidOp implements GraphOperation {

    public Matrix batchOp(Matrix input) {
        for (int i = 0; i < input.numRows(); i++) {
            for (int j = 0; i < input.numColumns(); j++) {
                input.set(i, j, 1.0 / (1.0 + Math.exp(-input.get(i, j))));
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
