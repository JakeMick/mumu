package com.mumu.core;

import com.mumu.core.operations.base.GraphOp;

import java.util.List;
import java.util.Set;

public class MumuFactory {
    public MueralNet loadFromProto(MumuGen.MuNet net) throws Exception {
        List<MumuGen.Node> layers = net.getLayersList();
        Mu networkBuilder = new Mu();
        for (MumuGen.Node layer: layers) {

            MumuGen.Node.LayerCase layerType = layer.getLayerCase();

            int parentId = getParentId(layer);
            int nodeId = getNodeId(layer);
            
            if (layerType.getNumber() == MumuGen.Node.ACTIVATIONNODE_FIELD_NUMBER) {
                MumuGen.ActivationNode activationNode = layer.getActivationNode();
                networkBuilder.addActivation(activationNode, nodeId, parentId);
            } else if (layerType.getNumber() == MumuGen.Node.DROPOUTNODE_FIELD_NUMBER) {
                MumuGen.DropoutNode dropoutNode = layer.getDropoutNode();
                networkBuilder.addDropout(dropoutNode, nodeId, parentId);
            } else if (layerType.getNumber() == MumuGen.Node.DENSENODE_FIELD_NUMBER) {
                MumuGen.DenseNode denseNode = layer.getDenseNode();
                networkBuilder.addDense(denseNode, nodeId, parentId);
            } else if (layerType.getNumber() == MumuGen.Node.NORMALIZATIONNODE_FIELD_NUMBER) {
                MumuGen.NormalizationNode normalizationNode = layer.getNormalizationNode();
                networkBuilder.addNormalization(normalizationNode, nodeId, parentId);
            } else {
                throw new Exception("Layer not implemented: " + String.valueOf(nodeId));
            }
        }
        Set<GraphOp> modelData = networkBuilder.build();
        return new MueralNet(modelData);
    }

    private int getNodeId(MumuGen.Node layer) {
        return layer.getId();
    }
    
    private int getParentId(MumuGen.Node layer) {
        return layer.getParent();
    }
}
