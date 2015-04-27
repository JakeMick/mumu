package com.mumu.core.operations.base;


import no.uib.cipr.matrix.Matrix;

public abstract class GraphOp implements Comparable<GraphOp> {
    private int     batch;
    private int     parentId;
    private int     nodeId;
    private int     inShape;
    private int     outShape;
    private Matrix  output;
    private boolean initialized;
    private GraphOp parentOp;

    public void connect(GraphOp op) {
        this.parentOp = op;
    }
    
    public Matrix predict(Matrix dataSet) {
        propagate(dataSet);
        return getOutput();
    }

    private void propagate(Matrix dataSet) {
        if (parentId != -1) {
            parentOp.propagate(dataSet);
            batchOpInplace(parentOp.getOutput());
        } else {
            batchOpInplace(dataSet);
        }
        
    }

    private void batchOpInplace(Matrix data) {
        setOutput(batchOp(data));
    }

    protected abstract void setBatch(int batch);

    public int getBatch() {
        return batch;
    }

    public int getParentId() {
        return this.parentId;
    }

    public int getIn() {
        return this.inShape;
    }

    public int getOut() {
        return this.outShape;
    }
    
    public abstract void initialize();

    public boolean isInitialized() {
        return initialized;
    }

    public abstract Matrix batchOp(Matrix input);

    public void setIn(int in) {
        this.inShape = in;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }
    
    @Override
    public int compareTo(GraphOp o) {
        GraphOp graphOp = (GraphOp) o;
        return Integer.compare(graphOp.getNodeId(), this.getNodeId());
    }

    public Matrix getOutput() {
        return output;
    }

    public void setOutput(Matrix output) {
        this.output = output;
    }
}
