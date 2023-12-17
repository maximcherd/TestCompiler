package ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для представления в AST-дереве оператора присваивания
 */
public class AssignNode extends StmtNode {
    private IdentNode var;
    private ExprNode val;

    public AssignNode(IdentNode var, ExprNode val) {
        this.var = var;
        this.val = val;
    }

    public AssignNode(Integer row, Integer col, IdentNode var, ExprNode val) {
        super(row, col);
        this.var = var;
        this.val = val;
    }

    @Override
    public String toString() {
        return "=";
    }

    @Override
    public List<AstNode> childs() {
        List<AstNode> astNodes = new ArrayList<>();
        astNodes.add(this.val);
        astNodes.add(this.var);
        return astNodes;
    }

    public IdentNode getVar() {
        return var;
    }

    public void setVar(IdentNode var) {
        this.var = var;
    }

    public ExprNode getVal() {
        return val;
    }

    public void setVal(ExprNode val) {
        this.val = val;
    }
}
