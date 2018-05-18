public class FunctionDouble extends StackElement {
    public static final FunctionDouble ZERO = new FunctionDouble(0);
    double value;

    public FunctionDouble(double value) {
        super();
        this.value = value;
    }

    public double evaluate(double[] var) {
        return value;
    }

    public String getText() {
        return String.valueOf(value);
    }
}
