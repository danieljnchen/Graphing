import java.util.ArrayList;

public class VectorFunction {
    private static ArrayList<char[]> variablesList = new ArrayList<>();
    private int vectorID;
    private Function[] functions;

    public VectorFunction(String[] functions, char[] variables) {
        this.functions = new Function[functions.length];
        variablesList.add(variables);
        vectorID = variablesList.size()-1;
        for(int i=0; i<functions.length; ++i) {
            this.functions[i] = new Function(functions[i],vectorID);
        }
    }

    public Vector evaluate(double[] variableValues) throws WrongParamNumberException {
        if(variablesList.get(vectorID).length != variableValues.length) {
            throw new WrongParamNumberException();
        }

        double[] out = new double[functions.length];
        for(int i=0; i<functions.length; ++i) {
            out[i] = functions[i].evaluate(variableValues);
        }
        return new Vector(out);
    }

    public int getDimension() {
        return functions.length;
    }

    public char[] getVariables() {
        return variablesList.get(vectorID);
    }

    public static char[] getVariables(int vectorID) {
        return variablesList.get(vectorID);
    }

    @Override
    public String toString() {
        String vars = "";
        for(int i=0; i<variablesList.get(vectorID).length; ++i) {
            vars += String.valueOf(variablesList.get(vectorID)[i]);
            if(i != variablesList.get(vectorID).length-1) {
                vars += ",";
            }
        }
        return "F(" + vars + ") = " + "(" + Parser.toSV(functions,";") + ")";
    }
}
