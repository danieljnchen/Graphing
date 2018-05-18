public class FunctionVar extends StackElement {
    private int varNumber;
    public FunctionVar(int varNumber) {
        this.varNumber = varNumber;
    }
    public double evaluate(double[] var) {
        return var[varNumber];
    }
}
