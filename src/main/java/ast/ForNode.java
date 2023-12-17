package ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для представления в AST-дереве цикла for
 */
public class ForNode extends StmtNode {
    private StmtNode init;
    private ExprNode cond;
    private StmtNode step;
    private StmtNode body;

    public ForNode(StmtNode init, ExprNode cond, StmtNode step, StmtNode body) {
        initNode(init, cond, step, body);
    }

    public ForNode(Integer row, Integer col, StmtNode init, ExprNode cond, StmtNode step, StmtNode body) {
        super(row, col);
        initNode(init, cond, step, body);
    }

    private void initNode(StmtNode init, ExprNode cond, StmtNode step, StmtNode body) {
        this.init = init != null ? init : AstBase.EMPTY_STMT;
        this.cond = cond != null ? cond : AstBase.EMPTY_STMT;
        this.step = step != null ? step : AstBase.EMPTY_STMT;
        this.body = body != null ? body : AstBase.EMPTY_STMT;
    }

    @Override
    public String toString() {
        return "for";
    }

    @Override
    public List<AstNode> childs() {
        List<AstNode> astNodes = new ArrayList<>();
        astNodes.add(this.init);
        astNodes.add(this.cond);
        astNodes.add(this.step);
        astNodes.add(this.body);
        return astNodes;
    }

    public StmtNode getInit() {
        return init;
    }

    public void setInit(StmtNode init) {
        this.init = init;
    }

    public ExprNode getCond() {
        return cond;
    }

    public void setCond(ExprNode cond) {
        this.cond = cond;
    }

    public StmtNode getStep() {
        return step;
    }

    public void setStep(StmtNode step) {
        this.step = step;
    }

    public StmtNode getBody() {
        return body;
    }

    public void setBody(StmtNode body) {
        this.body = body;
    }
}
