public class Tester {
    public static void main(String[] args) {
        Function fxn = new Function("3 + 4 ^ y + 4");
        System.out.println(fxn.evaluate(new double[] {3,4,5}));
        System.out.println(fxn.toString());
    }
}