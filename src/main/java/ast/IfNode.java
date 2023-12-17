package ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для представления в AST-дереве условного оператора
 */
public class IfNode extends StmtNode {
    private ExprNode cond;
    private StmtNode thenStmt;
    private StmtNode elseStmt;

    public IfNode(ExprNode cond, StmtNode thenStmt, StmtNode elseStmt) {
        this.cond = cond;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    public IfNode(Integer row, Integer col, ExprNode cond, StmtNode thenStmt, StmtNode elseStmt) {
        super(row, col);
        this.cond = cond;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public String toString() {
        return "if";
    }

    @Override
    public List<AstNode> childs() {
        List<AstNode> astNodes = new ArrayList<>();
        astNodes.add(this.cond);
        astNodes.add(this.thenStmt);
        if (this.elseStmt != null) {
            astNodes.add(this.elseStmt);
        }
        return astNodes;
    }

    public ExprNode getCond() {
        return cond;
    }

    public void setCond(ExprNode cond) {
        this.cond = cond;
    }

    public StmtNode getThenStmt() {
        return thenStmt;
    }

    public void setThenStmt(StmtNode thenStmt) {
        this.thenStmt = thenStmt;
    }

    public StmtNode getElseStmt() {
        return elseStmt;
    }

    public void setElseStmt(StmtNode elseStmt) {
        this.elseStmt = elseStmt;
    }
}
