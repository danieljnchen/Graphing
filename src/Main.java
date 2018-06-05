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
    public static char[] vars = {};

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
        Text functionLabel = new Text("f()");
        TextField function = new TextField();
        Button graphFunction = new Button("Load Function");
        graphFunction.setOnAction(actionEvent -> {
            currentFunction = new Function(function.getText());
            functionText.setText(currentFunction.toString());
        });
        functionInput.getChildren().addAll(functionLabel,function,graphFunction);

        HBox functionEvaluate = new HBox();
        functionEvaluate.setSpacing(10);
        Text variablesText = new Text("() = ");
        Text paramFormat = new Text("Format: number,number,number,...,number");
        TextField varsTextField = new TextField();
        Button evaluateFunction = new Button("Evaluate Function");
        evaluateFunction.setOnAction(actionEvent -> {
            System.out.println(currentFunction.evaluate(Parser.parseParams(varsTextField.getText())));
        });
        functionEvaluate.getChildren().addAll(variablesText,varsTextField,paramFormat,evaluateFunction);

        HBox variableInput = new HBox();
        variableInput.setSpacing(10);
        Label varsLabel = new Label("Variables");
        Text varsFormat = new Text("Format: char,char,...,char");
        Text variables = new Text();
        TextField varsInputField = new TextField();
        Button inputVars = new Button("Load Variables");
        inputVars.setOnAction(actionEvent -> {
            vars = Parser.parseVariables(varsInputField.getText());
            String varsText = "";
            for(char c : vars) {
                varsText = varsText + String.valueOf(c) + ",";
            }
            variables.setText(varsText.substring(0,varsText.length()-1));
            variablesText.setText("(" + variables.getText() + ") = ");
            functionLabel.setText("f(" + variables.getText() + ") = ");
        });
        variableInput.getChildren().addAll(varsLabel,varsInputField,inputVars,varsFormat);

        VBox all = new VBox();
        all.setSpacing(10);
        all.getChildren().addAll(functionInput,functionText,variableInput,functionEvaluate);
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
