public class Variable extends StackElement {
    private int varNumber;
    private int vectorID;
    public Variable(int varNumber, int vectorID) {
        this.varNumber = varNumber;
    }
    public double evaluate(double[] var) {
        return var[varNumber];
    }
    public String toString() {
        return String.valueOf(VectorFunction.getVariables(vectorID)[varNumber]);
    }
}
