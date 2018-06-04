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
    public static Function currentFunction;
    public static char[] vars = {'x', 'y', 'z'};

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
        String labelText = "f(";
        for(char c : vars) {
            labelText = labelText + String.valueOf(c) + ",";
        }
        labelText = labelText.substring(0,labelText.length()-1) + ")";
        Label label = new Label(labelText);
        TextField function = new TextField();
        Button graphFunction = new Button("Load Function");
        graphFunction.setOnAction(actionEvent -> {
            currentFunction = new Function(function.getText());
            functionText.setText(currentFunction.toString());
        });
        functionInput.getChildren().addAll(label,function,graphFunction);

        HBox functionEvaluate = new HBox();
        functionEvaluate.setSpacing(10);
        String label2Text = "(";
        for(char c : vars) {
            label2Text = label2Text + String.valueOf(c) + ",";
        }
        label2Text = label2Text.substring(0,label2Text.length()-1) + ") = ";
        Label label2 = new Label(label2Text);
        TextField vars = new TextField();
        Button evaluateFunction = new Button("Evaluate Function");
        evaluateFunction.setOnAction(actionEvent -> {
            try {
                System.out.println(currentFunction.evaluate(Parser.parseParams(vars.getText())));
            } catch(InvalidStringException e) {
                e.printStackTrace();
            }
        });
        functionEvaluate.getChildren().addAll(label2,vars,evaluateFunction);

        VBox all = new VBox();
        all.setSpacing(10);
        all.getChildren().addAll(functionInput,functionText,functionEvaluate);
        root.getChildren().add(all);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
            }
        }.start();
    }

    public static int getVarNumber(char c) {
        for(int i=0; i<vars.length; ++i) {
            if(vars[i] == c) {
                return i;
            }
        }
        System.out.println("Invalid variable character");
        return -1;
    }
}
