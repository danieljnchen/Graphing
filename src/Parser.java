import java.util.ArrayList;

public class Parser {
    public static ArrayList<StackElement> parseFunction(String function, int vectorID) {
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
            out.add(parseStackElement(function.substring(indices.get(i-1).intValue(),indices.get(i).intValue()),vectorID));
        }

        return infixToPostfix(out);
    }

    public static ArrayList<StackElement> infixToPostfix(ArrayList<StackElement> infix) {
       ArrayList<StackElement> out = new ArrayList<>();
       Stack operationStack = new Stack();

       StackElement previous = new StackDouble(0);
       for(int i=0; i<infix.size(); ++i) {
           StackElement curr = infix.get(i);
           if(curr instanceof Operation) {
               Operation operation = (Operation) curr;

               while(true) {
                   if((operation.getOperation() == Operation.Operations.SUBTRACT) && (i == 0 || (previous instanceof Parenthesis) || (previous instanceof Operation))) {
                       out.add(new StackDouble(-1));
                       operationStack.push(new Operation(Operation.Operations.MULTIPLY));
                       break;
                   }
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
                   break;
               }
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
           previous = curr;
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

    //TODO make more efficient
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

    public static StackElement parseStackElement(String element, int vectorID) {
        StackElement.Types type = determineType(element.charAt(0));
        if(type == StackElement.Types.DOUBLE) {
            return new StackDouble(Double.valueOf(element));
        } else if(type == StackElement.Types.VARIABLE) {
            char[] variables = VectorFunction.getVariables(vectorID);
            for(int i=0; i<variables.length; ++i) {
                if(variables[i] == element.charAt(0)) {
                    return new Variable(i,vectorID);
                }
            }
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

    public static double[] parseParams(String params) {
        params = Parser.removeSpaces(params) + ",";
        ArrayList<String> outArr = new ArrayList<>();
        int begin = 0;
        for(int next=0; next<params.length(); ++next) {
            if(params.charAt(next) == ',') {
                outArr.add(params.substring(begin,next));
                begin = next + 1;
                ++next; //there should be no adjacent commas
            }
        }
        double[] out = new double[outArr.size()];
        for(int i=0; i<outArr.size(); ++i) {
            try {
                out[i] = Double.parseDouble(outArr.get(i));
            } catch(NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return out;
    }

    public static char[] parseVariables(String variables) {
        /*variables = Parser.removeSpaces(variables) + ",";
        ArrayList<Character> outArr = new ArrayList<>();
        for(int i=0; i<variables.length(); ++i) {
            if(variables.charAt(i) == ',') {
                outArr.add(new Character(variables.charAt(i-1)));
            }
        }
        char[] out = new char[outArr.size()];
        for(int i=0; i<outArr.size(); ++i) {
            out[i] = outArr.get(i).charValue();
        }
        return out;*/
        String[] separated = separateCSV(variables);
        char[] out = new char[separated.length];
        for(int i=0; i<separated.length; ++i) {
            out[i] = separated[i].charAt(0);
        }
        return out;
    }

    public static String[] separateCSV(String csv) {
        csv = removeSpaces(csv);
        ArrayList<Integer> indices = new ArrayList<>();
        indices.add(new Integer(-1));
        for(int i=0; i<csv.length(); ++i) {
            if(csv.charAt(i) == ',') {
                indices.add(new Integer(i));
            }
        }
        indices.add(new Integer(csv.length()));

        String[] out = new String[indices.size()-1];
        for(int i=1; i<indices.size(); ++i) {
            out[i-1] = csv.substring(indices.get(i-1)+1,indices.get(i));
        }
        return out;
    }

    public static String toSV(Object[] objects, String delimiter) {
        String csv = "";
        for(int i=0; i<objects.length; ++i) {
            csv += objects[i].toString();
            if(i != objects.length-1) {
                csv += delimiter;
            }
        }
        return csv;
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