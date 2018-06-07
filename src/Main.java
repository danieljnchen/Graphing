import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    private static final double width = 925;
    private static final double height = 420;
    private static final double graphXPos = 515;
    private static final double graphYPos = 10;
    private static final double graphWidth = 400;
    private static final double graphHeight = 400;

    private static int updateStatus = -1;

    public static VectorFunction currentVectorFunction;
    public static Graph graph;

    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Grapher");
        primaryStage.getIcons().add(new Image("/Icon.jpg"));
        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        graph = new Graph(graphXPos,graphYPos,graphWidth,graphHeight,null);
        gc.clearRect(0,0,width,height);
        gc.setFill(Color.LIGHTGREY);
        gc.fillRect(0,0,width,height);
        graph.draw(gc);

        //Labels
        Text vectorFormat = new Text("Format: F(letter,letter,...,letter) = (function,function,...,function)");
        Label variableValuesLabel = new Label("Variable Values: ");
        Text variableValuesFormat = new Text("Format: number,number,...,number");
        Text instructions = new Text("No duplicate variable letters. e and i are not valid variable letters.");

        //Displays
        Text vectorText = new Text();
        Text variablesText = new Text();
        Text evaluateText = new Text();
        ImageView loading = new ImageView("LoadingIcon.gif");
        loading.setFitHeight(100);
        loading.setFitWidth(100);
        HBox loadingBox = new HBox();
        loadingBox.setVisible(false);
        loadingBox.getChildren().add(loading);

        //TextFields
        TextField vectorTextField = new TextField();
        TextField variablesTextField = new TextField();
        TextField variableValuesTextField = new TextField();
        TextField rotationAngleTextField = new TextField();

        //Buttons
        Button updateVectorFunction = new Button("Update VectorFunction");
        updateVectorFunction.setOnAction(actionEvent -> {
            currentVectorFunction = new VectorFunction(Parser.separateCSV(vectorTextField.getText()),Parser.parseVariables(variablesTextField.getText()));
            vectorText.setText("Current vector: " + currentVectorFunction.toString());

            char[] vars = currentVectorFunction.getVariables();
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
                evaluateText.setText("F(" + Parser.toSV(paramsStringArray,",") + ") = " + currentVectorFunction.evaluate(Parser.parseParams(variableValuesTextField.getText())).toString());
            } catch(WrongParamNumberException e) {
                evaluateText.setText("Wrong number of variables");
            }
        });

        Button updateRotationAngle = new Button("Update Rotation Angle");
        updateRotationAngle.setOnAction(actionEvent -> {
            graph.setRotationMatrix(Math.toRadians(Double.parseDouble(rotationAngleTextField.getText())));
        });

        Button updateGraph = new Button("Update Graph");
        updateGraph.setOnAction(actionEvent -> {
            ++updateStatus;
        });

        //HBoxes
        HBox vectorInput = new HBox();
        vectorInput.setSpacing(5);
        vectorInput.getChildren().addAll(new Text("F("),variablesTextField,new Text(") = "),vectorTextField,updateVectorFunction);

        HBox variableValuesInput = new HBox();
        variableValuesInput.setSpacing(5);
        variableValuesInput.getChildren().addAll(variableValuesLabel,variableValuesTextField,evaluateFunction);

        HBox display = new HBox();
        display.setSpacing(5);
        display.getChildren().addAll(vectorText,variablesText);

        HBox graphOptions = new HBox();
        graphOptions.setSpacing(5);
        graphOptions.getChildren().addAll(new Text("Rotation Angle: "),rotationAngleTextField,updateRotationAngle,updateGraph);

        VBox vector = new VBox();
        vector.setLayoutX(10);
        vector.setLayoutY(10);
        vector.setSpacing(10);
        vector.getChildren().addAll(vectorInput,vectorFormat,variableValuesInput,variableValuesFormat,display,evaluateText,graphOptions,loadingBox);
        root.getChildren().add(vector);

        canvas.addEventFilter(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {

        });

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                switch(updateStatus) {
                    case 1:
                        graph.setVectorFunction(currentVectorFunction);

                        gc.clearRect(0,0,width,height);
                        gc.setFill(Color.LIGHTGREY);
                        gc.fillRect(0,0,width,height);
                        break;
                    case 2:
                        loadingBox.setVisible(true);
                        break;
                    case 3:
                        graph.draw(gc);
                        break;
                    case 4:
                        loadingBox.setVisible(false);
                        break;
                }
                if(updateStatus != 0) {
                    updateStatus = (updateStatus + 1) % 5;
                }
            }
        }.start();
    }
}
