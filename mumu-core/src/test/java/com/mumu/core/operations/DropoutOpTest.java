package com.mumu.core.operations;

import com.mumu.core.operations.elem.DropoutOp;
import com.mumu.core.operations.base.ElementWise;
import no.uib.cipr.matrix.Matrix;

public class DropoutOpTest extends OpTest {

    private ElementWise op = new DropoutOp(0.5);

    public void testDropoutBatch() {
        Matrix input = ut.getSquare(50);
        op.batchOp(input);
        assertTrue(ut.allClose(input, 25));
    }
}