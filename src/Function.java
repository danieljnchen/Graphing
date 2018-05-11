public class Function {
    // operations supported: +, -, *, /, ^, log/ln, sin, cos, tan, csc, sec, cot, asin, acos, atan
    Function function1, function2;
    String operation; // arithmatic operation

    public Function() {

    }
    public Function(String function) throws InvalidStringException {
        String functionString = removeSpaces(function);

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
        function2 = FunctionDouble.ZERO;
    }

    public double evaluate(double[] var) throws InvalidOperationException {
        if(operation.equals("+") || operation.equals("-")) {
            return (operation.equals("+")?1:-1)*(function1.evaluate(var)+function2.evaluate(var));
        } else if(operation.equals("*") || operation.equals("/")) {
            return function1.evaluate(var) * Math.pow(function2.evaluate(var), (operation.equals("*") ? 1 : -1));
        } else if(operation.equals("^")) {
            return Math.pow(function1.evaluate(var), function2.evaluate(var));
        } else {
            throw new InvalidOperationException();
        }
    }

    public static String removeSpaces(String function) {
        while(function.contains(" ")) {
            function = function.substring(0,function.indexOf(" ")) + function.substring(function.indexOf(" ") + 1, function.length());
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
    public static boolean isValid(char in, char[] validChars) {
        for(char c : validChars) {
            if(in == c) {
                return true;
            }
        }
        return false;
    }

    public String getText() {
        return "(" + function1.getText() + operation + function2.getText() + ")";
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
        int length = 1;
        for(int i=0; i<params.length(); ++i) {
            if(params.charAt(i) == ',') {
                ++length;
            }
        }
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