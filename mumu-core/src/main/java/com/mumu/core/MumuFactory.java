package com.mumu.core;

import java.util.List;

public class MumuFactory {
    public void loadFromProto(MumuGen.MuNet net) throws Exception {
        List<MumuGen.Node> layers = net.getLayersList();
        Mu networkBuilder = new Mu();
        for (MumuGen.Node layer: layers) {
            MumuGen.Node.LayerCase layerType = layer.getLayerCase();
            if (layerType.getNumber() == MumuGen.Node.ACTIVATIONNODE_FIELD_NUMBER) {
                MumuGen.ActivationNode activationNode = layer.getActivationNode();
                networkBuilder.addActivation(activationNode);
            } else if (layerType.getNumber() == MumuGen.Node.DROPOUTNODE_FIELD_NUMBER) {
                MumuGen.DropoutNode dropoutNode = layer.getDropoutNode();
                networkBuilder.addDropout(dropoutNode);
            } else if (layerType.getNumber() == MumuGen.Node.DENSENODE_FIELD_NUMBER) {
                MumuGen.DenseNode denseNode = layer.getDenseNode();
                networkBuilder.addDense(denseNode);
            } else if (layerType.getNumber() == MumuGen.Node.NORMALIZATIONNODE_FIELD_NUMBER) {
                MumuGen.NormalizationNode normalizationNode = layer.getNormalizationNode();
                networkBuilder.addNormalization(normalizationNode);
            } else {
                throw new Exception("Not implemented" + layerType.getNumber());
            }
        }
        networkBuilder.build();
    }
}
