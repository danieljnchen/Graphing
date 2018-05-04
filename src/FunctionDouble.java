public class FunctionDouble extends Function {
    double value;

    public FunctionDouble(double value) {
        super();
        this.value = value;
    }

    @Override
    public double evaluate(double var) {
        return value;
    }
}
