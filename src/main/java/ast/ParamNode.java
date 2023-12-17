package ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для представления в AST-дереве объявления параметра функции
 */
public class ParamNode extends StmtNode {
    private TypeNode type;
    private IdentNode name;

    public ParamNode(TypeNode type, IdentNode name) {
        this.type = type;
        this.name = name;
    }

    public ParamNode(Integer row, Integer col, TypeNode type, IdentNode name) {
        super(row, col);
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.type.toString();
    }

    @Override
    public List<AstNode> childs() {
        List<AstNode> astNodes = new ArrayList<>();
        astNodes.add(this.name);
        return astNodes;
    }

    public TypeNode getType() {
        return type;
    }

    public void setType(TypeNode type) {
        this.type = type;
    }

    public IdentNode getName() {
        return name;
    }

    public void setName(IdentNode name) {
        this.name = name;
    }
}
