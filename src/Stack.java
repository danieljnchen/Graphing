import java.util.ArrayList;

public class Stack {
    private ArrayList<StackElement> stack = new ArrayList<>();

    public Stack() {
    }

    public void push(String s) {
        StackElement newElement = parseElement(s);
        if(newElement != null) {
            stack.add(newElement);
        }
    }

    public void push(StackElement element) {
        stack.add(element);
    }

    public StackElement parseElement(String s) {
        if(s.length() == 1) {
            char c = s.charAt(0);
            if(Parser.charContains(c, Function.operations)) {
                return new Operation(c);
            } else if(Parser.isLetter(c)) {
                return new Variable(Function.getVarNumber(c));
            }
        } else {
            try {
                double d = Double.parseDouble(s);
                return new StackDouble(d);
            } catch (NumberFormatException e) {
                System.out.println("String " + s + " could not be added to the stack");
            }
        }
        return null;
    }

    public StackElement pop() {
        return stack.remove(stack.size()-1);
    }

    public String toString() {
        String out = "";
        for(int i=0; i<stack.size(); ++i) {
            out = out + stack.get(i).toString() + ",";
        }
        return out.substring(0,out.length()-1);
    }
}
