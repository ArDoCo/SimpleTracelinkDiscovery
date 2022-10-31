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

    /**
     * Calculates the maximum possible value of the phi coefficient given the four values of the confusion matrix (TP,
     * FP, FN, TN).
     *
     * @see <a href="https://link.springer.com/article/10.1007/BF02288588">Paper about PhiMax by Ferguson (1941)</a>
     * @see <a href="https://journals.sagepub.com/doi/abs/10.1177/001316449105100403">Paper about Phi/PhiMax by
     *      Davenport et al. (1991)<</a>
     * @return The maximum possible value of phi.
     */
    public double phiCoefficientMax() {
        var tp = BigDecimal.valueOf(this.tp);
        var fp = BigDecimal.valueOf(this.fp);
        var fn = BigDecimal.valueOf(this.fn);
        var tn = BigDecimal.valueOf(this.tn);

        var test = fn.add(tp).compareTo(fp.add(tp)) >= 0;
        var nominator = (fp.add(tn)).multiply(tp.add(fp)).sqrt(MathContext.DECIMAL128);
        var denominator = (fn.add(tn)).multiply(tp.add(fn)).sqrt(MathContext.DECIMAL128);
        if (test) {
            // standard case
            return nominator.divide(denominator, MathContext.DECIMAL128).doubleValue();
        } else {
            // if test is not true, you have to swap nominator and denominator as then you have to mirror the confusion
            // matrix (,i.e., swap TP and TN)
            return denominator.divide(nominator, MathContext.DECIMAL128).doubleValue();
        }
    }

    /**
     * Calculates the normalized phi correlation coefficient value that is phi divided by its maximum possible value.
     *
     * @see <a href="https://journals.sagepub.com/doi/abs/10.1177/001316449105100403"> Paper about Phi/PhiMax</a> </a>
     * @return The value of Phi/PhiMax
     */
    public double phiOverPhiMax() {
        var phi = phiCoefficient();
        var phiMax = phiCoefficientMax();
        return phi / phiMax;
    }
}
