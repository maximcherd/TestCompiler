package semanticBase;

import exceptions.SemanticException;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для описания типов данных
 */
public class TypeDesc {
    public static TypeDesc VOID = new TypeDesc(BaseType.VOID);
    public static TypeDesc INT = new TypeDesc(BaseType.INT);
    public static TypeDesc FLOAT = new TypeDesc(BaseType.FLOAT);
    public static TypeDesc BOOL = new TypeDesc(BaseType.BOOL);
    public static TypeDesc STR = new TypeDesc(BaseType.STR);

    private BaseType baseType = null;
    private TypeDesc returnType = null;
    private List<TypeDesc> params = new ArrayList<TypeDesc>();

    public TypeDesc() {
    }

    public TypeDesc(BaseType baseType) {
        this.baseType = baseType;
    }

    public TypeDesc(BaseType baseType, TypeDesc returnType) {
        this.baseType = baseType;
        this.returnType = returnType;
    }

    public TypeDesc(BaseType baseType, TypeDesc returnType, List<TypeDesc> params) {
        this.baseType = baseType;
        this.returnType = returnType;
        this.params = params;
    }

    public boolean func() {
        return returnType != null;
    }

    public boolean isSimple() {
        return !this.func();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeDesc other = (TypeDesc) o;
        if (this.func() != other.func()) {
            return false;
        }
        if (!this.func()) {
            return this.baseType.equals(other.baseType);
        } else {
            if (!this.returnType.equals(other.returnType)) {
                return false;
            }
            if (this.params.size() != other.params.size()) {
                return false;
            }
            for (int i = 0; i < this.params.size(); i++) {
                if (!this.params.get(i).equals(other.params.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    public static TypeDesc fromBaseType(BaseType baseType) {
        return new TypeDesc(baseType);
    }

    public static TypeDesc fromStr(String strDec) throws Exception {
        try {
            BaseType baseType = BaseType.byValue(strDec);
            return new TypeDesc(baseType);
        } catch (Exception e) {
            throw new SemanticException("ERROR " + "unknown type " + strDec);
        }
    }

    @Override
    public String toString() {
        if (!this.func()) {
            return this.baseType.toString();
        } else {
            StringBuilder sb = new StringBuilder(returnType.toString());
            sb.append(" (");
            for (TypeDesc param : params) {
                if (sb.charAt(sb.length() -1) != '(') {
                    sb.append(", ");
                }
                sb.append(param.toString());
            }
            sb.append(')');
            return sb.toString();
        }
    }

    public BaseType getBaseType() {
        return baseType;
    }

    public void setBaseType(BaseType baseType) {
        this.baseType = baseType;
    }

    public TypeDesc getReturnType() {
        return returnType;
    }

    public void setReturnType(TypeDesc returnType) {
        this.returnType = returnType;
    }

    public List<TypeDesc> getParams() {
        return params;
    }

    public void setParams(List<TypeDesc> params) {
        this.params = params;
    }
}
