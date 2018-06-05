public class Tester {
    public static void main(String[] args) {
        char[] parsed = Parser.parseVariables("x,y,z,q");
        for(char d : parsed) {
            System.out.println(d);
        }
    }
}