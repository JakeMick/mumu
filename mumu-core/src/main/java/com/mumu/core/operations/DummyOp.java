package com.mumu.core.operations;

import com.mumu.core.operations.iface.GraphOperation;
import no.uib.cipr.matrix.Matrix;

/**
 * Created by derp on 4/25/15.
 */
public class DummyOp implements GraphOperation {
    public Matrix batchOp(Matrix input) {

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
