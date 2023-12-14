package semanticBase;

public enum ScopeType {
    GLOBAL("global"),
    GLOBAL_LOCAL("global.local"),
    PARAM("param"),
    LOCAL("local");

    private String value;

    ScopeType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
