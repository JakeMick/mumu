package com.mumu.core.graph;

import com.mumu.core.operations.base.GraphOp;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class GraphicalNeuralNetwork {
    private final List<GraphOp> ops = new ArrayList<>();

    /* Symbolic computation graph
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
    public void insert(GraphOp op) {
        ops.add(op);
    }

    public Set<GraphOp> build() throws Exception {
        Set<GraphOp> orphans = findOrphans();

        for (GraphOp op : orphans) {
            findHome(op);
        }
        return orphans;

    }

    private GraphOp findHome(GraphOp op) throws Exception {
        int parentId = op.getParentId();
        if (op.getParentId() == -1) {
            return op;
        } else {
            GraphOp parentOp = findNodeById(op.getParentId());
            findHome(parentOp);
            op.connect(parentOp);
        }
        return op;
    }

    private Set<GraphOp> findOrphans() throws Exception {
        TreeSet<Integer> parentLabels = new TreeSet<>();
        TreeSet<Integer> nodeLabels = new TreeSet<>();
        boolean sawAnAdult = false;
        for (GraphOp op : ops) {
            int parentId = op.getParentId();
            if (parentId == -1) {
                sawAnAdult = true;
            }
            parentLabels.add(parentId);

            int nodeId = op.getNodeId();
            if (nodeLabels.contains(nodeId)) {
                throw new Exception("Cannot use a node id twice");
            }
            nodeLabels.add(nodeId);
        }
        if (!sawAnAdult)
            throw new Exception("There is no entry point to the graph");

        nodeLabels.removeAll(parentLabels);
        if (nodeLabels.isEmpty())
            throw new Exception("Nothing left in graph");
        return nodesInNodeLabels(nodeLabels);
    }

    private GraphOp findNodeById(int nodeId) throws Exception {
        for (GraphOp op : ops) {
            if (nodeId == op.getNodeId()) {
                return op;
            }
        }
        throw new Exception("Cannot find parent of node");
    }

    private Set<GraphOp> nodesInNodeLabels(Set<Integer> nodeIds) {
        TreeSet<GraphOp> workingNodes = ops.stream()
                .filter(op -> nodeIds
                        .contains(op.getNodeId()))
                .collect(Collectors.toCollection(() -> new TreeSet<>()));
        return workingNodes;
    }
}
