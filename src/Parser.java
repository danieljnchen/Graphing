import java.util.ArrayList;

public class Parser {
    public static ArrayList<StackElement> parseFunction(String function) {
        ArrayList<StackElement> out = new ArrayList<>();
        function = removeSpaces(function);
        ArrayList<Integer> indices = new ArrayList<>();
        Operation.Types type = determineType(function.charAt(0));
        Operation.Types lastType = type;

        indices.add(new Integer(0));
        for(int i=0; i < function.length(); ++i) {
            type = determineType(function.charAt(i));
            if(type != lastType) {
                indices.add(new Integer(i));
                lastType = type;
            }
        }
        indices.add(new Integer(function.length()));

        for(int i=1; i<indices.size(); ++i) {
            out.add(parseStackElement(function.substring(indices.get(i-1).intValue(),indices.get(i).intValue())));
        }

        return infixToPostfix(out);
    }

    public static ArrayList<StackElement> infixToPostfix(ArrayList<StackElement> infix) {
       ArrayList<StackElement> out = new ArrayList<>();
       Stack operationStack = new Stack();

       for(int i=0; i<infix.size(); ++i) {
           StackElement curr = infix.get(i);
           if(curr instanceof Operation) {
               Operation operation = (Operation) curr;
               StackElement next = operationStack.peek();
               if((next == null) || (next instanceof Parenthesis)) {
               } else if(operation.getPriority() <= ((Operation) next).getPriority()) {
                   while(true) {
                       StackElement top = operationStack.peek();
                       if((top instanceof Operation) && (operation.getPriority() <= ((Operation) top).getPriority())) {
                           out.add(operationStack.pop());
                       } else {
                           break;
                       }
                   }
               }
               operationStack.push(operation);
           } else if(curr instanceof Parenthesis) {
               if(((Parenthesis) curr).getSide() == Parenthesis.Side.RIGHT) {
                   while (true) {
                       StackElement next = operationStack.peek();
                       if ((next == null) || ((next instanceof Parenthesis) && (((Parenthesis) next).getSide() == Parenthesis.Side.LEFT))) {
                           operationStack.pop();
                           break;
                       } else {
                           out.add(operationStack.pop());
                       }
                   }
               } else {
                   operationStack.push(curr);
               }
           } else {
               out.add(curr);
           }
       }
       while(true) {
           StackElement curr = operationStack.pop();
           if(curr == null) {
               break;
           } else {
               out.add(curr);
           }
       }

       return out;
    }

    public static Operation.Types determineType(char c) {
        if(isLetter(c)) {
            return StackElement.Types.VARIABLE;
        } else if(isNumber(c)) {
            return StackElement.Types.DOUBLE;
        } else if(isParenthesis(c)) {
            return StackElement.Types.PARENTHESIS;
        } else if(isOperation(c)) {
            return StackElement.Types.OPERATION;
        } else {
            return null;
        }
    }

    public static String removeSpaces(String function) {
        for (int i = 0; i < function.length();) {
            if (function.charAt(i) == ' ') {
                function = function.substring(0, i) + function.substring(i + 1, function.length());
            } else {
                ++i;
            }
        }
        return function;
    }

    public static StackElement parseStackElement(String element) {
        StackElement.Types type = determineType(element.charAt(0));
        if(type == StackElement.Types.DOUBLE) {
            return new StackDouble(Double.valueOf(element));
        } else if(type == StackElement.Types.VARIABLE) {
            return new Variable(Main.getVarNumber(element.charAt(0)));
        } else if(type == StackElement.Types.PARENTHESIS) {
            try {
                return new Parenthesis(element.charAt(0));
            } catch(InvalidStringException e) {
                e.printStackTrace();
            }
        } else if(type == StackElement.Types.OPERATION) {
            return new Operation(element.charAt(0));
        }
        return null;
    }

    public static double[] parseParams(String params) throws InvalidStringException {
        params = Parser.removeSpaces(params);
        if ((params.length() < 2) || (params.charAt(0) != '(') || (params.charAt(params.length() - 1) != ')') || params.contains("(,") || params.contains(",)") || params.contains(",,")) {
            throw new InvalidStringException();
        }
        if (params.length() == 2) {
            double[] out = {};
            return out;
        }
        ArrayList<Double> outArr = new ArrayList<>();
        params = params.substring(1);
        for (int i = 1; i < params.length(); ++i) {
            if (params.charAt(i) == ')') {
                break;
            } else if (params.charAt(i) == ',') {
                outArr.add(new Double(Double.parseDouble(params.substring(0, i))));
            }
        }
        double[] out = new double[outArr.size()];
        for (int i = 0; i < outArr.size(); ++i) {
            out[i] = outArr.get(i).doubleValue();
        }
        return out;
    }

    /*public static int parentheseMatch(String s, int index) {
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
    }*/

    public static boolean isNumber(char c) {
        return (c>47 && c<58) || c=='.';
    }
    public static boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
    public static boolean isParenthesis(char c) {
        return c == '(' || c == ')';
    }
    public static boolean isOperation(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    /*public static boolean charContains(char in, char[] validChars) {
        for(char c : validChars) {
            if(in == c) {
                return true;
            }
        }
        return false;
    }*/
}