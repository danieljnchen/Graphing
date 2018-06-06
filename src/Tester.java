public class Tester {
    public static void main(String[] args) {
        Vector v = new Vector(Parser.separateCSV("3*x+5,4/y ^2 +3, 230"),new char[] {'x','y'});
        System.out.println(v.toString());
        try {
            System.out.println(v.evaluate(new double[]{1, 2}));
        } catch(WrongParamNumberException e) {
            e.printStackTrace();
        }
    }
}