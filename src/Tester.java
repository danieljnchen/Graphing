public class Tester {
    public static void main(String[] args) {
        Matrix2D a = new Matrix2D(new double[][] {{3,3,8,289},{3,4,2,21},{34,2901,0,21.431239408748937},{3,22,130,30.2},{3,4,2,1},{3}});
        a.printMatrix();
        try {
            System.out.println(a.getDeterminant());
        } catch(InvalidMatrixException e) {
            e.printStackTrace();
        }
        /*try {
            a.invert();
        } catch(InvalidMatrixException e) {
            e.printStackTrace();
        }*/
        System.out.println();
        a.rref();
        a.printMatrix();
    }
}