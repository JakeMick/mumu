package com.mumu.core;

import junit.framework.TestCase;
import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrix;

import java.io.File;
import java.io.FileInputStream;

import static com.mumu.core.MumuGen.*;

public class MumuFactoryTest extends TestCase {

    private final String munetBinary = "../temp/nn.bin";
    private final String testData    = "../temp/data.csv";
    private       String doesntExist = "../YOLO";

    public void testIgnorantFileFindingLogic() throws Exception {
        File nowhere = new File(doesntExist);
        assertFalse(nowhere.exists());
    }

    public void testMuNetBinExists() throws Exception {
        File netHandle = new File(munetBinary);
        assertTrue(netHandle.exists());
    }
    
    public void testDataExists() throws Exception {
        File dataHandle = new File(testData);
        assertTrue(dataHandle.exists());
    }
    
    public void testLoadMuNet() throws Exception {
        File netHandle = new File(munetBinary);
        FileInputStream content = new FileInputStream(netHandle);
        MuNet muNet = MuNet.parseFrom(content);
        MumuFactory mumuFactory = new MumuFactory();
        MueralNet model = mumuFactory.loadFromProto(muNet);
        double[][] data = { {0, -1000}, {4.0, 4.0} };
        DenseMatrix dataset = new DenseMatrix(data);
        Matrix prediction = model.predict(dataset);
        System.out.println(prediction);
    }
    
    
    
}