public class IdentityMatrix extends Matrix2D {
    public IdentityMatrix(int size) {
        super(generateIdentity(size));
    }

    private static double[][] generateIdentity(int size) {
        double[][] out = new double[size][size];
        for(int i=0; i<size; ++i) {
            out[i][i] = 1;
        }
        return out;
    }
}
