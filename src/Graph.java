import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Graph {
    private double xPos;
    private double yPos;
    private double width;
    private double height;
    private static final double radius = 2;
    private static final double axesTickLength = 10;

    private VectorFunction vectorFunction;
    private double[] origin;
    //private Matrix2D rotationMatrix;
    public double[] bounds;
    private Matrix2D basisVectors;

    private ArrayList<Vector> graphPoints;
    private ArrayList<Vector> axesPoints;

    public Graph(double xPos, double yPos, double width, double height, VectorFunction vectorFunction) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        setVectorFunction(vectorFunction);
        //rotationMatrix = new Matrix2D(new double[][] {{1,0},{0,1}});
        graphPoints = new ArrayList<>();

        axesPoints = new ArrayList<>();

        for(double i=-10; i<=10; i+= .01) {
            axesPoints.add(new Vector(new double[] {i,0,0}));
            axesPoints.add(new Vector(new double[] {0,i,0}));
            axesPoints.add(new Vector(new double[] {0,0,i}));
        }
    }

    public void setVectorFunction(VectorFunction vectorFunction) {
        this.vectorFunction = vectorFunction;
        if(isVectorSet()) {
            origin = new double[vectorFunction.getDimension()];
            origin[0] = xPos + width/2;
            origin[1] = yPos + height/2;
            setBasisVectors();
            setBounds();
        }
    }

    public void setBasisVectors() {
        if(isVectorSet()) {
            if(basisVectors == null) {
                int dimension = vectorFunction.getDimension();
                basisVectors = new Matrix2D(dimension,dimension);
                for(int i=0; i<dimension; ++i) {
                    basisVectors.setElement(i,i,1);
                }
            }
        }
    }

    public void setBounds() {
        if(isVectorSet()) {
            bounds = new double[vectorFunction.getVarNum()*2];
            for(int i=0; i<bounds.length; ++i) {
                bounds[i] = 10 * ((i%2==0) ? -1:1);
            }
        }
    }

    /*public void setRotationMatrix(Matrix2D matrix) {
        rotationMatrix = matrix;
    }

    public void setRotationMatrix(double angle) {
        rotationMatrix = new Matrix2D(new double[][] {{Math.cos(angle),-Math.sin(angle)},{Math.sin(angle),Math.cos(angle)}});
    }*/

    public boolean isVectorSet() {
        return vectorFunction != null;
    }

    public void setOrigin(double[] origin) throws WrongParamNumberException {
        if(!isVectorSet()) {
            return;
        } else {
            if(origin.length != vectorFunction.getDimension()) {
                throw new WrongParamNumberException();
            }
            this.origin = origin;
        }
    }

    public void evaluateOverBoundsStart(double increment) {
        assert increment > 0;

        graphPoints.clear();
        evaluateOverBounds(increment,new double[]{});
    }

    private void evaluateOverBounds(double increment, double[] currentValues) {
        int boundSet = currentValues.length;
        if(boundSet < bounds.length/2) {
            for(double i=bounds[boundSet*2]; i<=bounds[boundSet*2+1]; i+=increment) {
                double[] newValues = new double[currentValues.length+1];
                for(int j=0; j<currentValues.length; ++j) {
                    newValues[j] = currentValues[j];
                }
                newValues[currentValues.length] = i;
                evaluateOverBounds(increment,newValues);
            }
        } else if(boundSet == bounds.length/2) {
            try {
                graphPoints.add(vectorFunction.evaluate(currentValues));
            } catch(WrongParamNumberException e) {
                e.printStackTrace();
            }
        }
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(xPos,yPos,width,height);

        if(isVectorSet()) {
            //axes
            /*gc.setStroke(Color.BLACK);
            gc.strokeLine(xPos, origin[1], xPos + width, origin[1]);
            gc.strokeLine(origin[0], yPos, origin[0], yPos + height);
            for(int i=0; i<=20; ++i) {
                gc.strokeLine(xPos+width*(i/20d),origin[1]-axesTickLength/2,xPos+width*(i/20d),origin[1]+axesTickLength/2);
                gc.strokeLine(origin[0]-axesTickLength/2,yPos+height*(i/20d),origin[0]+axesTickLength/2,yPos+height*(i/20d));
            }*/

            //function
            gc.setFill(Color.DARKBLUE);
            evaluateOverBoundsStart(.1);
            for(Vector v : graphPoints) {
                gc.fillOval(origin[0] + v.getElement(0)*10 - radius,origin[1] - v.getElement(1)*10 - radius, radius*2, radius*2);
            }
            gc.setFill(Color.BLACK);
            for(Vector v : axesPoints) {
                gc.fillOval(origin[0] + v.getElement(0) * 10 - radius, origin[1] - v.getElement(1) * 10 - radius, radius * 2, radius * 2);
            }
            /*Vector lastVector = new Vector(vectorFunction.getDimension());
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
                        gc.fillOval(origin[0] + vectorFinal.getElement(0)*10 - radius,origin[1] - vectorFinal.getElement(1)*10 - radius,radius*2,radius*2);
                    } catch(IncompatibleMatricesException e) {
                        e.printStackTrace();
                    }
                } catch(WrongParamNumberException e) {
                    e.printStackTrace();
                }
            }*/
        }
    }
}
