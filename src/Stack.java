import java.util.ArrayList;

public class Stack {
    private ArrayList<StackElement> stack = new ArrayList<>();

    public Stack() {
    }

    public void addElement(String s) {
        if(s.length() == 1) {
            char c = s.charAt(0);
            if(Function.charContains(c, Function.operations)) {
                stack.add(new Operation(c));
                return;
            } else if(Parser.isLetter(c)) {
                stack.add(new FunctionVar(Function.getVarNumber(c)));
            }
        }
        //@TODO parse double
    }

    public void addElement(StackElement element) {
        stack.add(element);
    }

    public void parseElement(String s) {
    }

    public StackElement pop() {
        return stack.remove(stack.size()-1);
    }
}
