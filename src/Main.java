import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Main extends Application {
    private static final double width = 700;
    private static final double height = 500;

    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Grapher");
        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        Matrix2D A = new Matrix2D(2,3);
        A.printMatrix();
        for(int r=0; r<A.getRows(); ++r) {
            for(int c=0; c<A.getCols(); ++c) {
                A.setElement(r*A.getCols()+c,r,c);
            }
        }
        A.printMatrix();

        Matrix2D B = new Matrix2D(2,3);
        for(int r=0; r<B.getRows(); ++r) {
            for(int c=0; c<B.getRows(); ++c) {
                B.setElement(2*(r*A.getCols()+c),r,c);
            }
        }
        B.printMatrix();

        try {
            Matrix2D sum = Matrix2D.addMatrices(A, B);
            sum.printMatrix();
        } catch(IncompatibleMatricesException e) {
            e.printStackTrace();
        }
    }
}
