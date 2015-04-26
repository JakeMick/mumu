package com.mumu.core.operations;

import com.mumu.core.operations.iface.GraphOperation;
import no.uib.cipr.matrix.Matrix;

public class DropoutOp implements GraphOperation {
    private double probability;

    public DropoutOp(double probability) {
        this.probability = probability;
    }

    public Matrix batchOp(Matrix input) {
        input.scale(probability);
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
