package ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для представления в AST-дереве условного оператора
 */
public class WhileNode extends StmtNode{
    private ExprNode cond;
    private StmtNode body;

    public WhileNode(ExprNode cond, StmtNode body) {
        this.cond = cond;
        this.body = body;
    }

    public WhileNode(Integer row, Integer col, ExprNode cond, StmtNode body) {
        super(row, col);
        this.cond = cond;
        this.body = body;
    }

    @Override
    public String toString() {
        return "while";
    }

    @Override
    public List<AstNode> childs() {
        List<AstNode> astNodes = new ArrayList<>();
        astNodes.add(this.cond);
        if (this.body != null) {
            astNodes.add(this.body);
        }
        return astNodes;
    }

    public ExprNode getCond() {
        return cond;
    }

    public void setCond(ExprNode cond) {
        this.cond = cond;
    }

    public StmtNode getBody() {
        return body;
    }

    public void setBody(StmtNode body) {
        this.body = body;
    }
}
