import java.util.ArrayList;

public class Function {
    // operations supported: +, -, *, /, ^, log/ln, sin, cos, tan, csc, sec, cot, asin, acos, atan
    //Function function1, function2;
    //String operation; // arithmatic operation
    public static final char[] operations = {'+', '-', '*', '/', '^'};
    private static char[] vars = {'x'};
    String function;

    public Function() {

    }
    public Function(String function) throws InvalidStringException {
        function = removeSpaces(function);
        //convert to postfix
        /*String functionString = removeSpaces(function);

        for(int i=0; i<functionString.length(); ++i) {
            if(!isValid(functionString.charAt(i))) {
                throw new InvalidStringException();
            }

            if(isOperation(functionString.charAt(i))) {
                function1 = new Function(functionString.substring(0,i));
                for(int j=1; i+j<functionString.length(); ++j) {
                    if(isNumber(functionString.charAt(i+j))) {
                        operation = functionString.substring(i,i+j);
                        System.out.println(operation);
                        function2 = new Function(functionString.substring(i+j,functionString.length()));
                        break;
                    }
                }
                return;
            } else if(functionString.charAt(i) == '(') {
                System.out.println("asjdflk;");
                int match = parentheseMatch(functionString,i);
                function1 = new Function(functionString.substring(i+1,match));
                if(match == functionString.length()-1) {
                    operation = "+";
                    function2 = FunctionDouble.ZERO;
                } else {
                    operation = functionString.substring(match+1,match+2);
                    function2 = FunctionDouble.ZERO;
                    function2 = new Function(functionString.substring(match+2));
                }
                return;
            }
        }
        function1 = new FunctionDouble(Double.valueOf(functionString));
        operation = "+";
        function2 = FunctionDouble.ZERO;*/
    }

    public double evaluate(double[] vars) throws InvalidOperationException {
        /*if(operation.equals("+") || operation.equals("-")) {
            return (operation.equals("+")?1:-1)*(function1.evaluate(var)+function2.evaluate(var));
        } else if(operation.equals("*") || operation.equals("/")) {
            return function1.evaluate(var) * Math.pow(function2.evaluate(var), (operation.equals("*") ? 1 : -1));
        } else if(operation.equals("^")) {
            return Math.pow(function1.evaluate(var), function2.evaluate(var));
        } else {
            throw new InvalidOperationException();
        }*/
        Stack stack = new Stack();
        for(int i=0; i<function.length(); ++i) {
            char curr = function.charAt(i);
            if(curr == ',') {
                stack.addElement(function.substring(0,i));
                function = function.substring(i+1,function.length());
            } else if(charContains(curr,operations)) {
                Operation operation = new Operation(curr);
                try {
                    stack.addElement(new FunctionDouble(operation.evaluate(new double[] {stack.pop().evaluate(vars), stack.pop().evaluate(vars)})));
                } catch(WrongParamNumberException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    public static int getVarNumber(char c) {
        for(int i=0; i<vars.length; ++i) {
            if(vars[i] == c) {
                return i;
            }
        }
        return -1;
    }

    public static String removeSpaces(String function) {
        for(int i=0; i<function.length(); ++i) {
            if(function.charAt(i) == ' ') {
                function = function.substring(0,i) + function.substring(i+1, function.length());
            }
        }
        System.out.println(function);
        return function;
    }

    public static int parentheseMatch(String s, int index) {
        if(s.charAt(index) != '(') {
            return -1;
        }

        int depth = 0;
        int pDepth = 0;
        for(int i=0; i<s.length(); ++i) {
            if(i<index) {
                if(s.charAt(i) == '(') {
                    ++depth;
                } else if(s.charAt(i) == ')') {
                    --depth;
                }
            } else if(i == index) {
                ++depth;
                pDepth = depth;
            } else if(s.charAt(i) == '(' || s.charAt(i) == ')') {
                if(s.charAt(i) == '(') {
                    ++depth;
                } else if(s.charAt(i) == ')') {
                    --depth;
                }
                if(depth == pDepth-1) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static boolean isNumber(char c) {
        return (c>47 && c<58) || c=='.';
    }
    public static boolean charContains(char in, char[] validChars) {
        for(char c : validChars) {
            if(in == c) {
                return true;
            }
        }
        return false;
    }

    public String getText() {
        return function;
    }

    public double[] parseParams(String params) throws InvalidStringException {
        params = removeSpaces(params);
        if((params.length()<2) || (params.charAt(0) != '(') || (params.charAt(params.length()-1) != ')') || params.contains("(,") || params.contains(",)") || params.contains(",,")) {
            throw new InvalidStringException();
        }
        if(params.length() == 2) {
            double[] out = {};
            return out;
        }
        ArrayList<Double> outArr = new ArrayList<>();
        params = params.substring(1);
        for(int i=1; i<params.length(); ++i) {
            if(params.charAt(i) == ')') {
                break;
            } else if(params.charAt(i) == ',') {
                outArr.add(new Double(Double.parseDouble(params.substring(0,i))));
            }
        }
        double[] out = new double[outArr.size()];
        for(int i=0; i<outArr.size(); ++i) {
            out[i] = outArr.get(i).doubleValue();
        }
        return out;
    }

    /*public static double getInitialNumber(String s) throws InvalidStringException {
        if (isNumber(s.charAt(0))) {
            for (int i = 0; i < s.length() - 1; ++i) {
                if (!isNumber(s.charAt(i + 1))) {
                    return Double.valueOf(s.substring(0, i + 1));
                }
            }
        }
        throw new InvalidStringException();
    }*/
}