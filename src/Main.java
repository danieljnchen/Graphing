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
    public static Vector currentVector;

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

        //HBox functionInput = new HBox();
        //functionInput.setSpacing(10);
        //Text functionLabel = new Text("f()");
        //Button graphFunction = new Button("Load Function");
        /*graphFunction.setOnAction(actionEvent -> {
            currentVector = new Vector(Parser.separateCSV(functionInputField.getText()), vars);
            vectorText.setText(currentVector.toString());
        });*/
        //functionInput.getChildren().addAll(functionLabel,functionInputField,graphFunction);

        //HBox functionEvaluate = new HBox();
        //functionEvaluate.setSpacing(10);
        //Text variablesText = new Text("() = ");
        //functionEvaluate.getChildren().addAll(variablesText,varsTextField,paramFormat,evaluateFunction);

        //HBox variableInput = new HBox();
        //variableInput.setSpacing(10);

        //Labels
        Text vectorFormat = new Text("Format: F(letter,letter,...,letter) = (function,function,...,function)");
        Label variableValuesLabel = new Label("Variable Values: ");
        Text variableValuesFormat = new Text("Format: number,number,...,number");

        //Displays
        Text vectorText = new Text();
        Text variablesText = new Text();
        Text evaluateText = new Text();

        //TextFields
        TextField vectorTextField = new TextField();
        TextField variablesTextField = new TextField();
        TextField variableValuesTextField = new TextField();

        //Buttons
        Button updateVector = new Button("Update Vector");
        updateVector.setOnAction(actionEvent -> {
            currentVector = new Vector(Parser.separateCSV(vectorTextField.getText()),Parser.parseVariables(variablesTextField.getText()));
            vectorText.setText("Current vector: " + currentVector.toString());

            char[] vars = currentVector.getVariables();
            Character[] varsArr = new Character[vars.length];
            for(int i=0; i<vars.length; ++i) {
                varsArr[i] = new Character(vars[i]);
            }
            variablesText.setText("Current Variables: " + Parser.toSV(varsArr,","));
        });

        Button evaluateFunction = new Button("Evaluate Function");
        evaluateFunction.setOnAction(actionEvent -> {
            try {
                double[] params = Parser.parseParams(variableValuesTextField.getText());
                String[] paramsStringArray = new String[params.length];
                for(int i=0; i<params.length; ++i) {
                    paramsStringArray[i] = String.valueOf(params[i]);
                }
                evaluateText.setText("F(" + Parser.toSV(paramsStringArray,",") + ") = " + currentVector.evaluate(Parser.parseParams(variableValuesTextField.getText())).toString());
            } catch(WrongParamNumberException e) {
                e.printStackTrace();
            }
        });

        //HBoxes
        HBox vectorInput = new HBox();
        vectorInput.setSpacing(10);
        vectorInput.getChildren().addAll(new Text("F("),variablesTextField,new Text(") = "),vectorTextField,updateVector);

        HBox variableValuesInput = new HBox();
        variableValuesInput.setSpacing(10);
        variableValuesInput.getChildren().addAll(variableValuesLabel,variableValuesTextField,evaluateFunction,variableValuesFormat);

        HBox display = new HBox();
        display.setSpacing(10);
        display.getChildren().addAll(vectorText,variablesText);

        /*Button inputVars = new Button("Load Variables");
        inputVars.setOnAction(actionEvent -> {
            vars = Parser.parseVariables(variablesInputField.getText());
            String varsText = "";
            for(char c : vars) {
                varsText = varsText + String.valueOf(c) + ",";
            }
            //variables.setText(varsText.substring(0,varsText.length()-1));
            //variablesText.setText("(" + variables.getText() + ") = ");
            //functionLabel.setText("f(" + variables.getText() + ") = ");
        });*/
        //variableInput.getChildren().addAll(varsLabel,varsInputField,inputVars,varsFormat);

        VBox all = new VBox();
        all.setSpacing(10);
        all.getChildren().addAll(vectorInput,vectorFormat,variableValuesInput,display,evaluateText);
        root.getChildren().add(all);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
            }
        }.start();
    }
}
