package com.mumu.core.operations;

import com.mumu.core.operations.elem.SoftplusOp;
import com.mumu.core.operations.base.ElementWise;
import no.uib.cipr.matrix.Matrix;

public class SoftplusOpTest extends OpTest {

    private ElementWise op = new SoftplusOp();

    public void testSoftplus() {
        Matrix data = ut.getSquare(-1.0);
        double magicConst = 0.31326169;
        assertFalse(ut.allClose(data, magicConst));
        op.batchOp(data);
        assertTrue(ut.allClose(data, magicConst));
    }

}