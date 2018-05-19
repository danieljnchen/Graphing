public class Function {
    // operations supported: +, -, *, /, ^, log/ln, sin, cos, tan, csc, sec, cot, asin, acos, atan
    //Function function1, function2;
    //String operation; // arithmatic operation
    public static final char[] operations = {'+', '-', '*', '/', '^'};
    private static char[] vars = {'x'};
    Stack stack;

    public Function() {

    }
    public Function(String function) throws InvalidStringException {
        function = Parser.removeSpaces(function);

        stack = new Stack();
        for(int i=0; i<function.length(); ++i) {
        }
    }

    public double evaluate(double[] vars) throws InvalidOperationException {
        return stack.evaluate(vars);
    }

    public static int getVarNumber(char c) {
        for(int i=0; i<vars.length; ++i) {
            if(vars[i] == c) {
                return i;
            }
        }
        return -1;
    }


    public String getText() {
        return stack.toString();
    }
}