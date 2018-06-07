import java.util.ArrayList;

public class Stack {
    private ArrayList<StackElement> stack = new ArrayList<>();

    public Stack() {
    }

    public void push(StackElement element) {
        stack.add(element);
    }

    public StackElement pop() {
        if(stack.size() > 0) {
            return stack.remove(stack.size() - 1);
        } else {
            return null;
        }
    }

    public StackElement peek() {
        if(stack.size() > 0) {
            return stack.get(stack.size() - 1);
        } else {
            return null;
        }
    }

    public String toString() {
        String out = "";
        for(int i=0; i<stack.size(); ++i) {
            out = out + stack.get(i).toString() + ",";
        }
        return out.substring(0,out.length()-1);
    }
}
