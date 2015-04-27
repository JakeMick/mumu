package com.mumu.core.operations;

import junit.framework.TestCase;

public abstract class OpTest extends TestCase {
    public Utility       ut;

    @Override
    public void setUp() {
        this.ut = new Utility();
    }
}
