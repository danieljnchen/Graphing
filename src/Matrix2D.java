public class Matrix2D {
    private double[][] matrix;

    public Matrix2D(double[][] matrix) {
        this.matrix = matrix;
        int cols = 0;
        for(int r=0; r<getRows(); ++r) {
            if(matrix[r].length > cols) {
                cols = matrix[r].length;
            }
        }
        for(int r=0; r<getRows(); ++r) {
            if(matrix[r].length < cols) {
                double[] newRow = new double[cols];
                for(int c=0; c<cols; ++c) {
                    if(c < matrix[r].length) {
                        newRow[c] = matrix[r][c];
                    }
                }
                matrix[r] = newRow;
            }
        }
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
    public void setElement(int row, int col, double in) throws ArrayIndexOutOfBoundsException {
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
    public void setRow(int row, Vector v) throws InvalidMatrixException {
        if(v.getRows() != getCols()) {
            throw new InvalidMatrixException();
        }
        for(int i=0; i<getCols(); ++i) {
            matrix[row][i] = v.getElement(i);
        }
    }
    public Vector getColumn(int col) throws ArrayIndexOutOfBoundsException {
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
                out.setElement(c,r,getElement(r,c));
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

    @Override
    public Matrix2D clone() {
        double[][] out = new double[getRows()][getCols()];
        for(int r=0; r<getRows(); ++r) {
            for(int c=0; c<getCols(); ++c) {
                out[r][c] = getElement(r,c);
            }
        }
        return new Matrix2D(out);
    }

    public static Matrix2D add(Matrix2D a, Matrix2D b) throws IncompatibleMatricesException {
        if(a.getRows() != b.getRows() || a.getCols() != b.getCols()) {
            throw new IncompatibleMatricesException();
        }
        Matrix2D out = new Matrix2D(a.getRows(),a.getCols());
        for(int r=0; r<a.getRows(); ++r) {
            for(int c=0; c<a.getCols(); ++c) {
                out.setElement(r,c,a.getElement(r,c)+b.getElement(r,c));
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
                out.setElement(r,c,Vector.dot(a.getRow(r),b.getColumn(c)));
            }
        }

        return out;
    }

    public static Matrix2D multiplyScalar(Matrix2D a, double k) {
        Matrix2D out = new Matrix2D(a.getRows(),a.getCols());
        for(int r=0; r<a.getRows(); ++r) {
            for(int c=0; c<a.getCols(); ++c) {
                out.setElement(r,c,a.getElement(r,c)*k);
            }
        }
        return out;
    }

    public void invert() throws InvalidMatrixException {
        if (Math.abs(getDeterminant()) < Math.pow(10, -6)) {
            throw new InvalidMatrixException();
        }

        Matrix2D b = new Matrix2D(getRows(), getCols() * 2);
        for (int r = 0; r < getRows(); ++r) {
            for (int c = 0; c < getCols(); ++c) {
                b.setElement(r, c, getElement(r, c));
            }
        }
        for (int r = 0; r < getRows(); ++r) {
            b.setElement(r, getCols() + r, 1);
        }

        b.rref();

        for(int r=0; r<getRows(); ++r) {
            for(int c=0; c<getCols(); ++c) {
                setElement(r,c,b.getElement(r,c+getCols()));
            }
        }
    }

    public Matrix2D getSubmatrix(int row1, int col1, int row2, int col2) throws InvalidParametersException {
        if(row1>row2 || col1>col2 || row2>getRows() || col2>getCols()) {
            throw new InvalidParametersException();
        }

        Matrix2D out = new Matrix2D(row2-row1,col2-col1);
        for(int r=row1; r<row2; ++r) {
            for(int c=col1; c<col2; ++c) {
                out.setElement(r-row1,c-col1,getElement(r,c));
            }
        }

        return out;
    }

    public Matrix2D rrefPreserveMatrix(Matrix2D a) {
        Matrix2D out = a.clone();
        out.rref();
        return out;
    }

    public void rref() {
        int colsDone = 0;
        for(int c=0; c<getCols() && c<getRows(); ++c) {
            for(int r=colsDone; r<getRows(); ++r) {
                if(getElement(r,c) != 0) {
                    multiplyRow(r,1/getElement(r,c));
                    swapRows(r,colsDone);
                    for(int ri=0; ri<getRows(); ++ri) {
                        if(ri == colsDone) {
                            continue;
                        }
                        addRow(ri,colsDone,getElement(ri,c)*-1);
                    }
                    ++colsDone;
                    break;
                }
            }
        }
    }

    public double getDeterminant() throws InvalidMatrixException {
        if(getRows() != getCols()) {
            throw new InvalidMatrixException("Matrix is not square");
        }
        if(getRows() == 2) {
            return getElement(0,0)*getElement(1,1) - getElement(0,1)*getElement(1,0);
        } else if(getRows() == 1) {
            return getElement(0,0);
        } else if(getRows() == 0) {
            throw new InvalidMatrixException("Matrix has no elements");
        } else {
            double out = 0;
            for(int c=0; c<getCols(); ++c) {
                out += getElement(0,c)*getSubmatrix(0,c).getDeterminant()*Math.pow(-1,c);
            }
            return out;
        }
    }

    public Matrix2D getSubmatrix(int row, int col) throws ArrayIndexOutOfBoundsException {
        if(row<0 || row>=getRows() || col<0 || col>= getCols()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Matrix2D out = new Matrix2D(getRows()-1,getCols()-1);
        for(int r=0; r<out.getRows(); ++r) {
            for(int c=0; c<out.getCols(); ++c) {
                out.setElement(r,c,getElement(r+((r>=row)?1:0),c+((c>=col)?1:0)));
            }
        }
        return out;
    }

    /**
     * @param row1
     * @param row2
     * @param scalar
     * @returns row1 + scalar*row2
     */
    public void addRow(int row1, int row2, double scalar) {
        for(int c=0; c<getCols(); ++c) {
            setElement(row1,c,getElement(row1,c)+getElement(row2,c)*scalar);
        }
    }
    public void multiplyRow(int row, double scalar) {
        try {
            setRow(row,Matrix2D.multiplyScalar(getRow(row), scalar).getColumn(0));
        } catch(InvalidMatrixException e) {
            e.printStackTrace();
        }
    }
    public void swapRows(int row1, int row2) {
        Vector tempRow = getRow(row1);
        try {
            setRow(row1,getRow(row2));
            setRow(row2,tempRow);
        } catch(InvalidMatrixException e) {
            e.printStackTrace();
        }
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
