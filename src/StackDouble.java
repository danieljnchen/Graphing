public class StackDouble extends StackElement {
    double value;

    public StackDouble(double value) {
        super();
        this.value = value;
    }

    public double evaluate(double[] var) {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
