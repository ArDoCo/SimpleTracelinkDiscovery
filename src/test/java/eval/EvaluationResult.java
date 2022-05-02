package eval;

public class EvaluationResult {
    int fn;
    int fp;
    int tp;

    public EvaluationResult(int tp, int fp, int fn) {
        this.fn = fn;
        this.fp = fp;
        this.tp = tp;
    }

    public EvaluationResult(){
        this.fn = 0;
        this.fp = 0;
        this.tp = 0;
    }

    public int getFn() {
        return fn;
    }

    public void setFn(int fn) {
        this.fn = fn;
    }

    public int getFp() {
        return fp;
    }

    public void setFp(int fp) {
        this.fp = fp;
    }

    public int getTp() {
        return tp;
    }

    public void setTp(int tp) {
        this.tp = tp;
    }

    public double precision(){
        return (double) tp/(tp+fp);
    }

    public double recall(){
        return (double) tp/(tp+fn);
    }

    public double f1(){
        return (2*precision()*recall())/(precision()+recall());
    }
}
