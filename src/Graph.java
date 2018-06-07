import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Graph {
    private double xPos;
    private double yPos;
    private double width;
    private double height;
    private static final double radius = 2;
    private static final double axesTickLength = 10;
    private VectorFunction vectorFunction;
    private double[] orgin;
    private Matrix2D rotationMatrix;

    public Graph(double xPos, double yPos, double width, double height, VectorFunction vectorFunction) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        setVectorFunction(vectorFunction);
        rotationMatrix = new Matrix2D(new double[][] {{1,0},{0,1}});
    }

    public void setVectorFunction(VectorFunction vectorFunction) {
        this.vectorFunction = vectorFunction;
        if(isVectorSet()) {
            orgin = new double[vectorFunction.getDimension()];
            orgin[0] = xPos + width/2;
            orgin[1] = yPos + height/2;
        }
    }

    public void setRotationMatrix(Matrix2D matrix) {
        rotationMatrix = matrix;
    }

    public void setRotationMatrix(double angle) {
        rotationMatrix = new Matrix2D(new double[][] {{Math.cos(angle),-Math.sin(angle)},{Math.sin(angle),Math.cos(angle)}});
    }

    public boolean isVectorSet() {
        return vectorFunction != null;
    }

    public void setOrgin(double[] orgin) throws WrongParamNumberException {
        if(!isVectorSet()) {
            return;
        } else {
            if(orgin.length != vectorFunction.getDimension()) {
                throw new WrongParamNumberException();
            }
            this.orgin = orgin;
        }
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(xPos,yPos,width,height);

        if(isVectorSet()) {
            //axes
            gc.setStroke(Color.BLACK);
            gc.strokeLine(xPos, orgin[1], xPos + width, orgin[1]);
            gc.strokeLine(orgin[0], yPos, orgin[0], yPos + height);
            for(int i=0; i<=20; ++i) {
                gc.strokeLine(xPos+width*(i/20d),orgin[1]-axesTickLength/2,xPos+width*(i/20d),orgin[1]+axesTickLength/2);
                gc.strokeLine(orgin[0]-axesTickLength/2,yPos+height*(i/20d),orgin[0]+axesTickLength/2,yPos+height*(i/20d));
            }

            //function
            gc.setFill(Color.DARKBLUE);
            Vector lastVector = new Vector(vectorFunction.getDimension());
            try {
                lastVector = vectorFunction.evaluate(new double[]{-11});
                System.out.println("lastVector");
                lastVector.printMatrix();
            } catch(WrongParamNumberException e) {
                e.printStackTrace();
            }
            for(double i=-20; i<=20;) {
                try {
                    Vector vector = vectorFunction.evaluate(new double[]{i});
                    System.out.println("vector: ");
                    vector.printMatrix();
                    try {
                        Matrix2D.add(vector,Matrix2D.multiplyScalar(lastVector, -1)).getCol(0).printMatrix();
                        double a = Vector.getMagnitude(Matrix2D.add(vector, Matrix2D.multiplyScalar(lastVector, -1)).getCol(0));
                        System.out.println("a: " + a);
                        i += 1/(1000*(a+1));
                        lastVector = vector;
                    } catch(IncompatibleMatricesException e) {
                        e.printStackTrace();
                    }
                    try {
                        Vector vectorFinal = Matrix2D.multiplyMatrices(rotationMatrix, vector).getCol(0);
                        gc.fillOval(orgin[0] + vectorFinal.getElement(0)*10 - radius,orgin[1] - vectorFinal.getElement(1)*10 - radius,radius*2,radius*2);
                    } catch(IncompatibleMatricesException e) {
                        e.printStackTrace();
                    }
                } catch(WrongParamNumberException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
