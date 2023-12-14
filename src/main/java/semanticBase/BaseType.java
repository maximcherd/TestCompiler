package semanticBase;

public enum BaseType {
    VOID("void"),
    INT("int"),
    FLOAT("float"),
    BOOL("bool"),
    STR("string");

    private String value;

    BaseType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
