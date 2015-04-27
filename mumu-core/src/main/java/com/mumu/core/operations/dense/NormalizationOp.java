package com.mumu.core.operations.dense;

import com.mumu.core.operations.base.Dense;
import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrix;
import no.uib.cipr.matrix.MatrixEntry;
import no.uib.cipr.matrix.Vector;

public class NormalizationOp extends Dense {
    private final double epsilon;
    private final Vector beta;
    private final Vector gamma;
    private final int    outShape;
    private final int    inShape;
    private double n     = 0;
    private double mu    = 0;
    private double mu2   = 0;
    private double delta = 0;
    private double x     = 0;
    private double sigma = 0;
    private double std   = 0;
    private DenseMatrix betaRep;
    private DenseMatrix gammaRep;

    public NormalizationOp(Vector beta, Vector gamma, double epsilon) {
        this.beta = beta;
        this.gamma = gamma;
        this.outShape = this.inShape = beta.size();
        this.epsilon = epsilon;
    }

    @Override
    public void setBatch(int batch) {
        this.batch = batch;
        initialize();
    }

    @Override
    public void initialize() {
        betaRep = makeVectorReplicate(batch, beta);
        gammaRep = makeVectorReplicate(batch, gamma);
    }

    protected Matrix apply(Matrix input) {
        mu = mean(input);
        std = stad(input, mu);
        for (MatrixEntry value : input) {
            value.set((value.get() - mu) / (std));            
        }
        for (int i = 0; i < batch; i++) {
            for (int j = 0; j < input.numColumns(); j++) {
                input.set(i, j, gamma.get(j) * input.get(i, j) + beta.get(j));
            }
        }
        return input;
    }

    private double mean(Matrix input) {
        double sum = 0.0;
        for (MatrixEntry i : input) {
            sum += i.get();
        }
        sum /= (input.numColumns() * input.numRows());
        return sum;
    }

    private double stad(Matrix input, double mu) {
        double diff = 0.0;
        double t;
        for (MatrixEntry i : input) {
            t = i.get() - mu;
            diff += t * t;
        }
        diff /= (input.numColumns() * input.numRows());
        return Math.sqrt(diff) + epsilon;
    }

    private void onlineStats(Matrix input) {
        // TODO extend Keras to include online estimates of the mean and variance
        // TODO get rid of this stupid hack
        if (n < 200000) {
            for (MatrixEntry value : input) {
                x = value.get();
                n += 1;
                delta = x - mu;
                mu += delta / n;
                mu2 += delta * (x - mu);
            }
            sigma = mu2 / (n - 1);
            std = sigma * sigma + epsilon;
        }
    }
}
