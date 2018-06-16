public class Tester {
    public static void main(String[] args) {
        Matrix2D a = new Matrix2D(new double[][] {{3,3,8,289},{3,4,2,21},{0,0,0,0},{3,22,130,30.2}});
        a.printMatrix();
        try {
            System.out.println(a.getDeterminant());
        } catch(InvalidMatrixException e) {
            e.printStackTrace();
        }
        a.rref();
        a.printMatrix();
    }
}