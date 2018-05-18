public class Operation extends StackElement {
    private Operations operation;

    enum Operations {
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE,
        POWER,
        LOG,
        LN,
        SIN,
        COS,
        TAN,
        CSC,
        SEC,
        COT,
        ASIN,
        ACOS,
        ATAN
    }

    public Operation(Operations operation) {
        this.operation = operation;
    }

    public Operation(char operation) {
        switch(operation) {
            case '+':
                this.operation = Operations.ADD;
                break;
            case '-':
                this.operation = Operations.SUBTRACT;
                break;
            case '*':
                this.operation = Operations.MULTIPLY;
                break;
            case '/':
                this.operation = Operations.DIVIDE;
                break;
            case '^':
                this.operation = Operations.POWER;
                break;
        }
    }

    public Operations getOperation() {
        return operation;
    }

    public double evaluate(double[] vars) throws WrongParamNumberException {
        if(vars.length != getParamNums()) {
            throw new WrongParamNumberException();
        }

        switch(operation) {
            case ADD:
                return vars[0] + vars[1];
            case SUBTRACT:
                return vars[0] - vars[1];
            case MULTIPLY:
                return vars[0] * vars[1];
            case DIVIDE:
                return vars[0] / vars[1];
            case POWER:
                return Math.pow(vars[0],vars[1]);
        }
        return -1;
    }

    public int getParamNums() {
        switch(operation) {
            case ADD:
                return 2;
            case SUBTRACT:
                return 2;
            case MULTIPLY:
                return 2;
            case DIVIDE:
                return 2;
            case POWER:
                return 2;
        }
        return -1;
    }
}
