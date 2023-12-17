package ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для представления в AST-дереве объявления переменных
 */
public class VarsNode extends StmtNode {
    private TypeNode type;
    private List<ExprNode> vars;

    /**
     * @param vars list of IdentNode or AssignNode
     */
    public VarsNode(TypeNode type, List<ExprNode> vars) {
        this.type = type;
        this.vars = vars;
    }

    public VarsNode(Integer row, Integer col, TypeNode type, List<ExprNode> vars) {
        super(row, col);
        this.type = type;
        this.vars = vars;
    }

    @Override
    public String toString() {
        return this.type.toString();
    }

    @Override
    public List<AstNode> childs() {
        return new ArrayList<>(this.vars);
    }

    public TypeNode getType() {
        return type;
    }

    public void setType(TypeNode type) {
        this.type = type;
    }

    public List<ExprNode> getVars() {
        return vars;
    }

    public void setVars(List<ExprNode> vars) {
        this.vars = vars;
    }
}
