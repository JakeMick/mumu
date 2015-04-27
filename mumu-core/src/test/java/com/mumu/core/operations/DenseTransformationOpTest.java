package com.mumu.core.operations;

import com.mumu.core.operations.dense.DenseTransformationOp;
import com.mumu.core.operations.elem.ReluOp;
import com.mumu.core.operations.base.ElementWise;
import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.DenseVector;
import no.uib.cipr.matrix.Matrix;
import no.uib.cipr.matrix.Vector;

public class DenseTransformationOpTest extends OpTest {
    
    private DenseTransformationOp getTestOp() {
        double[][] weightData = {{0.4, 0.5}, {0.6, 0.7}};
        Matrix weight = new DenseMatrix(weightData);
        double[] biasData = {0.1, 0.2};
        Vector bias = new DenseVector(biasData);
        ElementWise relu = new ReluOp();
        return new DenseTransformationOp(weight, bias, relu);
    }
    
    public void testDenseWithRelu() {
        DenseTransformationOp op = getTestOp();
        double[][] data = {{1., -2.},{3., 4.}};
        Matrix inputData = new DenseMatrix(data);
        inputData = op.batchOp(inputData);
        double[][] expectedResult = {{0.0, 0.0},{3.70, 4.50}};
        DenseMatrix expectedOutput = new DenseMatrix(expectedResult);
        DenseMatrix out = new DenseMatrix(2,2);
        out.zero();
        inputData.scale(-1);
        inputData.add(expectedOutput);
        assertTrue(ut.allClose(inputData, 0));
    }
}