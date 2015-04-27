package com.mumu.core.operations;

import com.mumu.core.operations.elem.SigmoidOp;
import com.mumu.core.operations.base.ElementWise;
import no.uib.cipr.matrix.Matrix;

/**
 * Created by derp on 4/26/15.
 */
public class SigmoidOpTest extends OpTest {

    private ElementWise op = new SigmoidOp();

    public void testSigmoid() {
        double magicConst = 0.26894142;
        Matrix data = ut.getSquare(-1.0);
        assertFalse(ut.allClose(data, magicConst));
        op.batchOp(data);
        assertTrue(ut.allClose(data, magicConst));
    }

}