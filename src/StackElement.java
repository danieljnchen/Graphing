public abstract class StackElement {
    public enum Types {
        PARENTHESIS,
        OPERATION,
        VARIABLE,
        DOUBLE
    }
    public abstract double evaluate(double[] vars);
    public abstract String getText();
}
