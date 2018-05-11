public class Adder extends Operation {
    public Adder() {
        paramNum = 2;
    }

    public double evaluate(Function[] function, double[] params) throws WrongParamNumberException {
        if(function.length != 2) {
            throw new WrongParamNumberException();
        }

        return function[0].evaluate(params) + function[1].evaluate(params);
    }
}
