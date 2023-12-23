package semanticBase;

import exceptions.SemanticException;

import java.util.HashMap;

/**
 * Класс для представлений областей видимости переменных во время семантического анализа
 */
public class IdentScope {
    private HashMap<String, IdentDesc> idents = new HashMap<>();
    private IdentScope parent = null;
    private IdentDesc func = null;
    private Integer varIndex = 0;
    private Integer paramIndex = 0;

    public IdentScope() {
    }

    public IdentScope(IdentScope parent) {
        this.parent = parent;
    }

    public boolean isGlobal() {
        return this.parent == null;
    }

    public IdentScope currGlobal() {
        IdentScope curr = this;
        while (curr.getParent() != null) {
            curr = curr.getParent();
        }
        return curr;
    }

    public IdentScope currFunc() {
        IdentScope curr = this;
        while (curr != null && curr.getFunc() == null) {
            curr = curr.getParent();
        }
        return curr;
    }

    public IdentDesc getIdent(String name) {
        IdentScope scope = this;
        IdentDesc ident = null;
        while (scope != null) {
            ident = scope.getIdents().get(name);
            if (ident != null) {
                break;
            }
            scope = scope.getParent();
        }
        return ident;
    }

    public IdentDesc addIdent(IdentDesc ident) throws SemanticException {
        IdentScope funcScope = this.currFunc();
        IdentScope globalScope = this.currGlobal();

        if (!ident.getScope().equals(ScopeType.PARAM)) {
            ident.setScope(funcScope != null ? ScopeType.LOCAL :
                    (this.equals(globalScope) ? ScopeType.GLOBAL : ScopeType.GLOBAL_LOCAL));
        }

        IdentDesc oldIdent = this.getIdent(ident.getName());
        if (oldIdent != null) {
            boolean error = false;
            if (ident.getScope().equals(ScopeType.PARAM)) {
                if (oldIdent.getScope().equals(ScopeType.PARAM)) {
                    error = true;
                }
            } else if (ident.getScope().equals(ScopeType.LOCAL)) {
                if (!oldIdent.getScope().equals(ScopeType.GLOBAL)
                        && !oldIdent.getScope().equals(ScopeType.GLOBAL_LOCAL)) {
                    error = true;
                }
            } else {
                error = true;
            }
            if (error) {
                throw new SemanticException(String.format("Идентификатор %s уже объявлен", ident.getName()));
            }
        }

        if (!ident.getType().func()) {
            if (ident.getScope().equals(ScopeType.PARAM)) {
                ident.setIndex(funcScope.getParamIndex());
                funcScope.setParamIndex(funcScope.getParamIndex() + 1);
            } else {
                IdentScope identScope = funcScope != null ? funcScope : globalScope;
                ident.setIndex(identScope.getVarIndex());
                identScope.setVarIndex(identScope.getVarIndex() + 1);
            }
        }

        this.idents.put(ident.getName(), ident);
        return ident;
    }

    public HashMap<String, IdentDesc> getIdents() {
        return idents;
    }

    public void setIdents(HashMap<String, IdentDesc> idents) {
        this.idents = idents;
    }

    public IdentScope getParent() {
        return parent;
    }

    public void setParent(IdentScope parent) {
        this.parent = parent;
    }

    public IdentDesc getFunc() {
        return func;
    }

    public void setFunc(IdentDesc func) {
        this.func = func;
    }

    public Integer getVarIndex() {
        return varIndex;
    }

    public void setVarIndex(Integer varIndex) {
        this.varIndex = varIndex;
    }

    public Integer getParamIndex() {
        return paramIndex;
    }

    public void setParamIndex(Integer paramIndex) {
        this.paramIndex = paramIndex;
    }
}
