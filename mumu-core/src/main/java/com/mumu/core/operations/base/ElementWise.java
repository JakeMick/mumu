package com.mumu.core.operations.base;

import no.uib.cipr.matrix.Matrix;

public abstract class ElementWise extends GraphOp {
    private final boolean initialize = true;

    public abstract Matrix batchOp(Matrix input);

    public void setBatch(int batch) {
    }
    
    public void initialize() {}
    
    public boolean isInitialized() {
        return true;
    }

}
