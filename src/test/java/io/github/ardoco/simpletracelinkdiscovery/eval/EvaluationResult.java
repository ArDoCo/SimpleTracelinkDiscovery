/* Licensed under MIT 2022. */
package io.github.ardoco.simpletracelinkdiscovery.eval;

import java.math.BigDecimal;
import java.math.MathContext;

public class EvaluationResult {
    private int fn;
    private int fp;
    private int tp;
    private int tn;

    public EvaluationResult(int tp, int fp, int fn, int tn) {
        this.fn = fn;
        this.fp = fp;
        this.tp = tp;
        this.tn = tn;
    }

    public double precision() {
        return (double) tp / (tp + fp);
    }

    public double recall() {
        return (double) tp / (tp + fn);
    }

    public double f1() {
        return (2 * precision() * recall()) / (precision() + recall());
    }

    public double accuracy() {
        double numerator = tp + tn;
        double denominator = tp + fp + fn + tn;
        return numerator / denominator;
    }

    public double specificity() {
        double specificity = ((double) tn) / ((double) tn + fp);
        if (Double.isNaN(specificity)) {
            return 1.0;
        }
        return specificity;
    }

    public double phiCoefficient() {
        var tp = BigDecimal.valueOf(this.tp);
        var fp = BigDecimal.valueOf(this.fp);
        var fn = BigDecimal.valueOf(this.fn);
        var tn = BigDecimal.valueOf(this.tn);

        var num = tp.multiply(tn).subtract((fp.multiply(fn)));

        var a = tp.add(fp);
        var b = tp.add(fn);
        var c = tn.add(fp);
        var d = tn.add(fn);
        if (a.equals(BigDecimal.ZERO) || b.equals(BigDecimal.ZERO) || c.equals(BigDecimal.ZERO) || d.equals(BigDecimal.ZERO)) {
            return 0d;
        }

        var productOfSumsInDenominator = a.multiply(b).multiply(c).multiply(d);
        var denominator = productOfSumsInDenominator.sqrt(MathContext.DECIMAL128);

        return num.divide(denominator, MathContext.DECIMAL128).doubleValue();
    }
}
