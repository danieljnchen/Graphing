public class Tester {
    public static void main(String[] args) {
        Variable v = new Variable(Main.getVarNumber('y'));
        System.out.println(v.evaluate(new double[] {3,4,5}));
        System.out.println(v.getText());
    }
}
