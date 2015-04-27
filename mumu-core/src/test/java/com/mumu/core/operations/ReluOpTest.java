package com.mumu.core.operations;

import com.mumu.core.operations.elem.ReluOp;
import com.mumu.core.operations.base.ElementWise;
import no.uib.cipr.matrix.Matrix;

public class ReluOpTest extends OpTest {

    private ElementWise op = new ReluOp();

    public void testRelu() {
        Matrix data = ut.getSquare(-20);
        assertFalse(ut.allClose(data, 0.0));
        op.batchOp(data);
        assertTrue(ut.allClose(data, 0.0));
    }

}