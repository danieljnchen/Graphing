public class Function {
    // operations supported: +, -, *, /, ^, log/ln, sin, cos, tan, csc, sec, cot, asin, acos, atan
    Function function1, function2;
    String operation; // arithmatic operation

    public Function() {

    }
    public Function(String function) throws InvalidStringException {
        function = removeSpaces(function);
        if(!isNumber(function.charAt(0)) || !isNumber(function.charAt(function.length()-1))) {
            throw new InvalidStringException();
        }
        for(int i=0; i<function.length(); ++i) {
            if(!isNumber(function.charAt(i))) {
                function1 = new Function(function.substring(0,i));
                operation = function.substring(i,i+1);
                function2 = new Function(function.substring(i+1,function.length()));
                return;
            }
        }
        function1 = new FunctionDouble(Double.valueOf(function));
        operation = "+";
        function2 = FunctionDouble.ZERO;
    }

    public double evaluate(double var) throws InvalidOperationException {
        if(operation.equals("+") || operation.equals("-")) {
            return (operation.equals("+")?1:-1)*(function1.evaluate(var)+function2.evaluate(var));
        } else if(operation.equals("*") || operation.equals("/")) {
            return Math.pow(function1.evaluate(var)*function2.evaluate(var),(operation.equals("*")?1:-1));
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

    public static boolean isNumber(char c) {
        return (c>47 && c<58) || c=='.';
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