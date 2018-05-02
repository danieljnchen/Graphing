public class Matrix2D {
    private double[][] matrix;

    public Matrix2D(double[][] matrix) {
        this.matrix = matrix;
    }
    public Matrix2D(int rows, int cols) throws IndexOutOfBoundsException {
        if(rows < 0 || cols < 0) {
            throw new IndexOutOfBoundsException();
        }
        matrix = new double[rows][cols];
    }

    public double[][] getMatrix() {
        return matrix;
    }
    public double getElement(int row, int col) throws IndexOutOfBoundsException {
        if(row >= getRows() || col >= getCols()) {
            throw new IndexOutOfBoundsException();
        }
        return matrix[row][col];
    }
    public void setElement(double in, int row, int col) throws IndexOutOfBoundsException {
        if(row >= getRows() || col >= getCols()) {
            throw new IndexOutOfBoundsException();
        }
        matrix[row][col] = in;
    }
    public int getRows() {
        return matrix.length;
    }
    public int getCols() {
        return matrix[0].length;
    }
    public double[] getRow(int row) {
        return matrix[row];
    }
    public double[] getCol(int col) {
        double[] out = new double[getRows()];
        for(int r=0; r<getRows(); ++r) {
            out[r] = getElement(r,col);
        }
        return out;
    }

    public static Matrix2D addMatrices(Matrix2D a, Matrix2D b) throws IncompatibleMatricesException {
        if(a.getRows() != b.getRows() || a.getCols() != b.getCols()) {
            throw new IncompatibleMatricesException();
        }
        Matrix2D out = new Matrix2D(a.getRows(),a.getCols());
        for(int r=0; r<a.getRows(); ++r) {
            for(int c=0; c<a.getCols(); ++c) {
                out.setElement(a.getElement(r,c)+b.getElement(r,c), r, c);
            }
        }
        return out;
    }

    public void printMatrix() {
        for(int r=0; r<getRows(); ++r) {
            for(int c=0; c<getCols(); ++c) {
                System.out.print(getElement(r,c)+" ");
            }
            System.out.println();
        }
    }
}
