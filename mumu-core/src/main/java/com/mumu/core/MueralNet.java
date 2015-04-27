package com.mumu.core;

import com.mumu.core.operations.base.GraphOp;
import no.uib.cipr.matrix.Matrix;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Set;

public class MueralNet {
    private final GraphOp model;

    public MueralNet(Set<GraphOp> modelData) throws NotImplementedException {
        if (modelData.size() != 1) {
            throw new NotImplementedException();
        }
        model = modelData.iterator().next();
    }
    
    public Matrix predict(Matrix input) {
        return model.predict(input);
    }
}
