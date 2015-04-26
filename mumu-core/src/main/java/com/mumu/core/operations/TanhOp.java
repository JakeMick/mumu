package com.mumu.core.operations;

import com.mumu.core.operations.iface.GraphOperation;
import no.uib.cipr.matrix.Matrix;

public class TanhOp implements GraphOperation {

    public Matrix batchOp(Matrix input) {
        for (int i = 0; i < input.numRows(); i++) {
            for (int j = 0; j < input.numColumns(); j++) {
                input.set(i, j, Math.tanh(input.get(i, j)));
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
