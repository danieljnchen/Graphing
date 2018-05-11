public abstract class Operation {
    protected int paramNum;
    public abstract double evaluate(Function[] functions) throws WrongParamNumberException;
}
