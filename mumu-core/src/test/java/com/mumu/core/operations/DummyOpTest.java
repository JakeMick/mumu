package com.mumu.core.operations;

import com.mumu.core.operations.elem.DummyOp;
import com.mumu.core.operations.base.ElementWise;
import no.uib.cipr.matrix.Matrix;

public class DummyOpTest extends OpTest {

    private ElementWise op = new DummyOp();

    public void testDummy() {
        Matrix data = ut.getSquare(2.0);
        op.batchOp(data);
        assertTrue(ut.allClose(data, 2.0));
    }

}