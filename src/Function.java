import java.util.ArrayList;

public class Function {
    // operations supported: +, -, *, /, ^, log/ln, sin, cos, tan, csc, sec, cot, asin, acos, atan
    //Function function1, function2;
    //String operation; // arithmatic operation
    public static final char[] operations = {'+', '-', '*', '/', '^'};
    ArrayList<StackElement> function;

    public Function(String function) throws InvalidStringException {
        this.function = Parser.parseFunction(function);
    }

    public double evaluate(double[] vars) {
        Stack evaluateStack = new Stack();
        for (int i = 0; i < function.size(); ++i) {
            StackElement curr = function.get(i);
            if (curr instanceof Operation) {
                Operation operation = (Operation) curr;
                double[] operands = new double[operation.getParamNum()];
                for (int j = 0; j < operation.getParamNum(); ++j) {
                    operands[j] = evaluateStack.pop().evaluate(vars);
                }
                try {
                    evaluateStack.push(new StackDouble(operation.evaluateFunction(operands)));
                } catch (WrongParamNumberException e) {
                    e.printStackTrace();
                }
            } else {
                evaluateStack.push(new StackDouble(curr.evaluate(vars)));
            }
        }
        return evaluateStack.pop().evaluate(vars);
    }

        public static int getVarNumber(char c) {
        for(int i=0; i<Main.vars.length; ++i) {
            if(Main.vars[i] == c) {
                return i;
            }
        }
        return -1;
    }


    public String getText() {
        String out = "";
        for(StackElement s : function) {
            out = out + s.getText() + ",";
        }
        return out.substring(0,out.length()-1);
    }
}