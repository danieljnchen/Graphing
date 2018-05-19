import java.util.ArrayList;

public class Stack {
    private ArrayList<StackElement> stack = new ArrayList<>();

    public Stack() {
    }

    public void addElement(String s) {
        if(s.length() == 1) {
            char c = s.charAt(0);
            if(Parser.charContains(c, Function.operations)) {
                stack.add(new Operation(c));
                return;
            } else if(Parser.isLetter(c)) {
                stack.add(new Variable(Function.getVarNumber(c)));
            }
        }
        //@TODO parse double
    }

    public void addElement(StackElement element) {
        stack.add(element);
    }

    public void parseElement(String s) {
        //@TODO parse element
    }

    public StackElement pop() {
        return stack.remove(stack.size()-1);
    }

    public double evaluate(double[] vars) {
        ArrayList<StackDouble> evaluateStack = new ArrayList<>();
        for(int i=0; i<stack.size(); ++i) {
            StackElement curr = stack.get(i);
            if(stack.get(i) instanceof Operation) {
                Operation operation = (Operation) curr;
                double[] operands = new double[operation.getParamNum()];
                for(int j=0; j<operation.getParamNum(); ++j) {
                    operands[j] = evaluateStack.remove(evaluateStack.size()-1).evaluate(vars);
                }
                try {
                    evaluateStack.add(new StackDouble(operation.evaluateFunction(operands)));
                } catch(WrongParamNumberException e) {
                    e.printStackTrace();
                }
            } else {
                evaluateStack.add(new StackDouble(curr.evaluate(vars)));
            }
        }
        return evaluateStack.get(evaluateStack.size()-1).evaluate(vars);
    }

    public String toString() {
        String out = "";
        for(int i=0; i<stack.size(); ++i) {
            out = out + stack.get(i).toString() + ",";
        }
        return out.substring(0,out.length()-1);
    }
}
