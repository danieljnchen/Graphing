public abstract class StackElement {
    public enum Types {
        OPERATION,
        VARIABLE,
        DOUBLE
    }
    public abstract double evaluate(double[] vars);
    public abstract String getText();
}
