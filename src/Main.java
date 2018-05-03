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
        for(int r=0; r<2; ++r) {
            for(int c=0; c<3; ++c) {
                A.setElement(r+c,r,c);
            }
        }
        A.printMatrix();
        A.transpose().printMatrix();

        try {
            System.out.println(Matrix2D.dot(A.getRow(0).transpose(), A.getRow(1).transpose()));
            Matrix2D.multiplyMatrices(A,A.transpose()).printMatrix();
        } catch(IncompatibleMatricesException e) {
            e.printStackTrace();
        }
        Matrix2D.multiplyScalar(A,2).printMatrix();
    }
}
