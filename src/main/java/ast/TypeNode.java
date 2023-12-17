package ast;

import semanticBase.TypeDesc;

/**
 * Класс для представления в AST-дереве типов данный
 * (при появлении составных типов данных должен быть расширен)
 */
public class TypeNode extends IdentNode {
    private TypeDesc type = null;

    public TypeNode(String name) {
        super(name);
        initNode(name);
    }

    public TypeNode(Integer row, Integer col, String name) {
        super(row, col, name);
        initNode(name);
    }

    private void initNode(String name) {
        try {
            this.type = TypeDesc.fromStr(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toStringFull() {
        return this.toString();
    }

    public TypeDesc getType() {
        return type;
    }

    public void setType(TypeDesc type) {
        this.type = type;
    }
}
