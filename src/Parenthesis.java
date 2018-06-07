public class Parenthesis extends StackElement {
    private Side side;

    public enum Side {
        LEFT,
        RIGHT
    }

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
        System.out.println("Parenthesis was evaluated");
        return 0;
    }


    public Side getSide() {
        return side;
    }

    public String toString() {
        if(side == Side.LEFT) {
            return "(";
        } else {
            return ")";
        }
    }
}
