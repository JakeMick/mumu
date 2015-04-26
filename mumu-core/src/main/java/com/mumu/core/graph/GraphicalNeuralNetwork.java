package com.mumu.core.graph;

import com.mumu.core.operations.iface.GraphOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphicalNeuralNetwork {
    private Map<Integer, List<GraphOperation>> ops = new HashMap<Integer, List<GraphOperation>>();

    /* Mental Model
             0
            / \
           2   4
          / \   \
         5   6   7
          \ /   / \
           8   9   \
            \ /     \
             10     11
     */
    public void insert(GraphOperation op) {
        List<GraphOperation> derivesFrom = ops.get(op.getParent());
        if (derivesFrom == null) {
            derivesFrom = new ArrayList<GraphOperation>();
        }
        derivesFrom.add(op);
        ops.put(op.getParent(), derivesFrom);
    }
}
