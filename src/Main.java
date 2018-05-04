import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {
    private static final double width = 700;
    private static final double height = 500;
    private static Function currentFunction;

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

        HBox functionInput = new HBox();
        functionInput.setLayoutX(10);
        functionInput.setLayoutY(10);
        functionInput.setSpacing(10);
        Label label = new Label("y = ");
        TextField function = new TextField();
        Button graphFunction = new Button("Graph Function");
        graphFunction.setOnAction(actionEvent -> {
            try {
                currentFunction = new Function(function.getText());
                System.out.println(currentFunction.evaluate(3));
            } catch(InvalidOperationException | InvalidStringException e) {
                e.printStackTrace();
            }
        });
        functionInput.getChildren().addAll(label,function,graphFunction);
        root.getChildren().add(functionInput);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {

            }
        }.start();
    }
}
