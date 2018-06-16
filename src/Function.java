import java.util.ArrayList;

public class Function {
    ArrayList<StackElement> function;

    public Function(String function, int vectorID) {
        this.function = Parser.parseFunction(function, vectorID);
    }

    public double evaluate(double[] vars) {
        Stack evaluateStack = new Stack();
        for (int i = 0; i < function.size(); ++i) {
            StackElement curr = function.get(i);
            if (curr instanceof Operation) {
                Operation operation = (Operation) curr;
                double[] operands = new double[operation.getParamNum()];
                for (int j = operation.getParamNum() - 1; j >= 0; --j) {
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
        return evaluateStack.peek().evaluate(vars);
    }



    public String toString() {
        /*String out = "";
        for(StackElement s : function) {
            out = out + s.toString() + ",";
        }*/
        StackElement[] functionArr = new StackElement[function.size()];
        for(int i=0; i<function.size(); ++i) {
            functionArr[i] = function.get(i);
        }
        return Parser.toSV(functionArr,",");
    }
}