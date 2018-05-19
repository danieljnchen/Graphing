public class StackDouble extends StackElement {
    public static final StackDouble ZERO = new StackDouble(0);
    double value;

    public StackDouble(double value) {
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
