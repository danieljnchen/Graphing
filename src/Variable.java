//verified
public class Variable extends StackElement {
    private int varNumber;
    public Variable(int varNumber) {
        this.varNumber = varNumber;
    }
    public double evaluate(double[] var) {
        return var[varNumber];
    }
    public String getText() {
        return String.valueOf(Main.vars[varNumber]);
    }
}
