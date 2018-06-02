public class Parenthesis extends StackElement {
    public Side side;

    public Parenthesis(char in) throws InvalidStringException {
        if(in == '(') {
            side = Side.LEFT;
        } else if(in == ')') {
            side = Side.RIGHT;
        } else {
            throw new InvalidStringException();
        }
    }

    @Override
    public double evaluate(double[] vars) {
        return 0;
    }

    public enum Side {
        LEFT,
        RIGHT
    }

    public String getText() {
        if(side == Side.LEFT) {
            return "(";
        } else {
            return ")";
        }
    }
}
