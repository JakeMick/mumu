package com.mumu.core.operations;

import com.mumu.core.operations.elem.TanhOp;
import com.mumu.core.operations.base.ElementWise;
import no.uib.cipr.matrix.Matrix;

public class TanhOpTest extends OpTest {

    private ElementWise op = new TanhOp();

    public void testTanh() {
        double magicConst = -0.76159416;
        Matrix data = ut.getSquare(-1.0);
        assertFalse(ut.allClose(data, magicConst));
        op.batchOp(data);
        assertTrue(ut.allClose(data, magicConst));
    }

}