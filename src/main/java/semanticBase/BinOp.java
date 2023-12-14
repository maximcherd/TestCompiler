package semanticBase;

public enum BinOp {

    ADD("+"),
    SUB("-"),
    MUL("*"),
    DIV("/"),
    MOD("%"),
    GT(">"),
    LT("<"),
    GE(">="),
    LE("<="),
    EQUALS("=="),
    NEQUALS("!="),
    BIT_AND("&"),
    BIT_OR("|"),
    LOGICAL_AND("&&"),
    LOGICAL_OR("||");

    private String value;

    BinOp(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static BinOp byValue(String value) {
        for (BinOp binOp : values()) {
            if (binOp.value.equals(value)) {
                return binOp;
            }
        }
        return null;
    }

}
