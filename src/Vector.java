public class Vector extends Matrix2D {
    public Vector(int rows) {
        super(rows,1);
    }

    public Vector(double[] matrix) {
        super(vectorToMatrix(matrix));
    }

    public static double[][] vectorToMatrix(double[] matrix) {
        double[][] wrapperMatrix = new double[matrix.length][1];
        for(int i=0; i<matrix.length; ++i) {
            wrapperMatrix[i][0] = matrix[i];
        }
        return wrapperMatrix;
    }

    public double getElement(int row) {
        return super.getElement(row,0);
    }

    public static double getMagnitude(Vector v) throws IncompatibleMatricesException {
        return Math.sqrt(dot(v, v));
    }

    public static double dot(Vector a, Vector b) throws IncompatibleMatricesException {
        if(a.getRows() != b.getRows()) {
            throw new IncompatibleMatricesException();
        }
        double out = 0;
        for(int r=0; r<a.getRows(); ++r) {
            out += a.getElement(r)*b.getElement(r);
        }
        return out;
    }
}
