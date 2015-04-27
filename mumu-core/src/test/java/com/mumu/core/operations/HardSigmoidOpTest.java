package com.mumu.core.operations;

import com.mumu.core.operations.elem.HardSigmoidOp;
import com.mumu.core.operations.base.ElementWise;
import no.uib.cipr.matrix.Matrix;

public class HardSigmoidOpTest extends OpTest {

    private ElementWise op = new HardSigmoidOp();

    public void testWayNegativeLimit() {
        Matrix data = ut.getSquare(-25);
        op.batchOp(data);
        assertTrue(ut.allClose(data, 0.0));
    }

    public void testScaling() {
        Matrix data = ut.getSquare(2.0);
        op.batchOp(data);
        assertTrue(ut.allClose(data, 0.9));
    }
    

    public void testLowerLimit() {
        Matrix data = ut.getSquare(-2.5);
        op.batchOp(data);
        assertTrue(ut.allClose(data, 0.0));
    }
    
    public void testUpperLimit() {
        Matrix data = ut.getSquare(2.5);
        op.batchOp(data);
        assertTrue(ut.allClose(data, 1.0));
    }
    
    public void testWayPositiveLimit() {
        Matrix data = ut.getSquare(25);
        op.batchOp(data);
        assertTrue(ut.allClose(data, 1.0));
    }

}