package com.mumu.core.operations.dense;

import com.mumu.core.operations.OpTest;
import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.DenseVector;
import no.uib.cipr.matrix.Vector;

public class NormalizationOpTest extends OpTest {    
    public void testNormalization() {

        double[] betaData = {0,0};
        Vector beta = new DenseVector(betaData);
        double[] gammaData = {0.045765,  0.02327648};
        Vector gamma = new DenseVector(gammaData);
        double eps = 1e-06;
        
        double[][] data = {{0.01842667, 0.02363018},
                           {0.07906121, -0.07539749}};
        DenseMatrix inputs = new DenseMatrix(data);
        NormalizationOp op = new NormalizationOp(beta, gamma, eps);
        op.batchOp(inputs);

        System.out.println(inputs);
        
    }

}