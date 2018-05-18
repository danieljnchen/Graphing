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

    public Operations getOperation() {
        return operation;
    }
}
