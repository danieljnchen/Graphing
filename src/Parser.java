import java.util.ArrayList;

public class Parser {
    public static boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static String removeSpaces(String function) {
        for (int i = 0; i < function.length();) {
            if (function.charAt(i) == ' ') {
                function = function.substring(0, i) + function.substring(i + 1, function.length());
            } else {
                ++i;
            }
        }
        System.out.println(function);
        return function;
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
}
