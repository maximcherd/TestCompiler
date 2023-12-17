package ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для представления в AST-дереве оператора return
 */
public class ReturnNode extends StmtNode {
    private ExprNode val;

    public ReturnNode(ExprNode val) {
        this.val = val;
    }

    public ReturnNode(Integer row, Integer col, ExprNode val) {
        super(row, col);
        this.val = val;
    }

    @Override
    public String toString() {
        return "return";
    }

    @Override
    public List<AstNode> childs() {
        List<AstNode> astNodes = new ArrayList<>();
        astNodes.add(this.val);
        return astNodes;
    }

    public ExprNode getVal() {
        return val;
    }

    public void setVal(ExprNode val) {
        this.val = val;
    }
}
