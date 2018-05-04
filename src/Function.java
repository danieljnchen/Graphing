public class Function {
    // operations supported: +, -, *, /, ^, log/ln, sin, cos, tan, csc, sec, cot, asin, acos, atan
    Function function1, function2;
    String function;
    String operation; // arithmatic operation

    public Function() {

    }
    public Function(String function) {
        setFunction(function);
        Function[] functions = parseFunction();
        function1 = functions[0];
        function2 = functions[1];
    }
    public void setFunction(String function) {
        this.function = function;
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

    public Function[] parseFunction() {
        Function[] out = new Function[2];
        function = removeSpaces(function);
        if(function.contains("+")) {
            out[0] = new Function(function.substring(0, function.indexOf("+")));
            out[1] = new Function(function.substring(function.indexOf("+") + 1, function.length()));
        } else {
        }
        try {
        } catch(Exception e) {
            e.printStackTrace();
        }
        return out;
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