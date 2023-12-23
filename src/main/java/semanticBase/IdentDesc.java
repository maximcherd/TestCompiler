package semanticBase;

/**
 * Класс для описания переменных
 */
public class IdentDesc {
    private String name;
    private TypeDesc type;
    private ScopeType scope = ScopeType.GLOBAL;
    private Integer index = 0;
    private boolean builtIn = false;

    public IdentDesc(String name, TypeDesc type) {
        this.name = name;
        this.type = type;
    }

    public IdentDesc(String name, TypeDesc type, ScopeType scope, Integer index) {
        this.name = name;
        this.type = type;
        this.scope = scope != null ? scope : this.scope;
        this.index = index != null ? index : this.index;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s",
                this.type.toString(), this.scope.toString(), this.builtIn ? "built-in" : this.index.toString());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeDesc getType() {
        return type;
    }

    public void setType(TypeDesc type) {
        this.type = type;
    }

    public ScopeType getScope() {
        return scope;
    }

    public void setScope(ScopeType scope) {
        this.scope = scope;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public boolean isBuiltIn() {
        return builtIn;
    }

    public void setBuiltIn(boolean builtIn) {
        this.builtIn = builtIn;
    }
}
