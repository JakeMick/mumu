package com.mumu.core.operations.iface;

import no.uib.cipr.matrix.Matrix;

public interface GraphOperation {
    public Matrix batchOp(Matrix input);
    
    public int getParent();
    
    public int getIn();
    
    public int getOut();
}
