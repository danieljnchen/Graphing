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
    public double getElement(int row, int col) throws ArrayIndexOutOfBoundsException {
        if(row >= getRows() || col >= getCols()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return matrix[row][col];
    }
    public void setElement(double in, int row, int col) throws ArrayIndexOutOfBoundsException {
        if(row >= getRows() || col >= getCols()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        matrix[row][col] = in;
    }
    public int getRows() {
        return matrix.length;
    }
    public int getCols() {
        return matrix[0].length;
    }
    public Vector getRow(int row) throws ArrayIndexOutOfBoundsException {
        return new Vector(matrix[row]);
    }
    public Vector getCol(int col) throws ArrayIndexOutOfBoundsException {
        double[] out = new double[getRows()];
        for(int r=0; r<getRows(); ++r) {
            out[r] = getElement(r,col);
        }
        return new Vector(out);
    }
    public Matrix2D transpose() {
        Matrix2D out = new Matrix2D(getCols(),getRows());
        for(int r=0; r<getRows(); ++r) {
            for(int c=0; c<getCols(); ++c) {
                out.setElement(getElement(r,c),c,r);
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

    public static Matrix2D add(Matrix2D a, Matrix2D b) throws IncompatibleMatricesException {
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

    public static Matrix2D multiplyMatrices(Matrix2D a, Matrix2D b) throws IncompatibleMatricesException {
        if(a.getCols() != b.getRows()) {
            throw new IncompatibleMatricesException();
        }
        Matrix2D out = new Matrix2D(a.getRows(),b.getCols());

        for(int r=0; r<a.getRows(); ++r) {
            for(int c=0; c<b.getCols(); ++c) {
                out.setElement(Vector.dot(a.getRow(r),b.getCol(c)),r,c);
            }
        }

        return out;
    }

    public static Matrix2D multiplyScalar(Matrix2D a, double k) {
        Matrix2D out = new Matrix2D(a.getRows(),a.getCols());
        for(int r=0; r<a.getRows(); ++r) {
            for(int c=0; c<a.getCols(); ++c) {
                out.setElement(a.getElement(r,c)*k,r,c);
            }
        }
        return out;
    }

    @Override
    public String toString() {
        String out = "";
        for(int r=0; r<getRows(); ++r) {
            for(int c=0; c<getCols(); ++c) {
                out += String.valueOf(matrix[r][c]);
                if(c != getCols()-1) {
                    out += ",";
                }
            }
            if(r != getRows()-1) {
                out += ";";
            }
        }
        return out;
    }
}
