public class FunctionDouble extends Function {
    public static final FunctionDouble ZERO = new FunctionDouble(0);
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
