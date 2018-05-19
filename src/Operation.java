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

    public double evaluate(double[] vars) {
        System.out.println("Operation was evaluated");
        return 0;
    }

    public double evaluateFunction(double[] operands) throws WrongParamNumberException {
        if(operands.length != getParamNum()) {
            throw new WrongParamNumberException();
        }

        switch(operation) {
            case ADD:
                return operands[0] + operands[1];
            case SUBTRACT:
                return operands[0] - operands[1];
            case MULTIPLY:
                return operands[0] * operands[1];
            case DIVIDE:
                return operands[0] / operands[1];
            case POWER:
                return Math.pow(operands[0],operands[1]);
        }
        return -1;
    }

    public int getParamNum() {
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
