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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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

        Text functionText = new Text();

        HBox functionInput = new HBox();
        functionInput.setSpacing(10);
        Label label = new Label("y = ");
        TextField function = new TextField();
        Button graphFunction = new Button("Load Function");
        graphFunction.setOnAction(actionEvent -> {
            try {
                currentFunction = new Function(function.getText());
                functionText.setText(currentFunction.getText());
            } catch(InvalidStringException e) {
                e.printStackTrace();
            }
        });
        functionInput.getChildren().addAll(label,function,graphFunction);

        HBox functionEvaluate = new HBox();
        functionEvaluate.setSpacing(10);
        Label label2 = new Label("x = ");
        TextField var = new TextField();
        Button evaluateFunction = new Button("Evaluate Function");
        evaluateFunction.setOnAction(actionEvent -> {
            try {
                System.out.println(currentFunction.evaluate(Double.valueOf(var.getText())));
            } catch(InvalidOperationException e) {
                e.printStackTrace();
            }
        });
        functionEvaluate.getChildren().addAll(label2,var,evaluateFunction);

        VBox all = new VBox();
        all.setSpacing(10);
        all.getChildren().addAll(functionInput,functionText,functionEvaluate);
        root.getChildren().add(all);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
            }
        }.start();
    }
}
