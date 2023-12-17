package ast;

import semanticBase.BinOp;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для представления в AST-дереве бинарных операций
 */
public class BinOpNode extends ExprNode {
    private BinOp op;
    private ExprNode arg1;
    private ExprNode arg2;

    public BinOpNode(BinOp op, ExprNode arg1, ExprNode arg2) {
        this.op = op;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public BinOpNode(String op, ExprNode arg1, ExprNode arg2) {
        this.op = BinOp.byValue(op);
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public BinOpNode(Integer row, Integer col, BinOp op, ExprNode arg1, ExprNode arg2) {
        super(row, col);
        this.op = op;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public String toString() {
        return op .toString();
    }

    @Override
    public List<AstNode> childs() {
        List<AstNode> astNodes = new ArrayList<>();
        astNodes.add(this.arg1);
        astNodes.add(this.arg2);
        return astNodes;
    }

    public BinOp getOp() {
        return op;
    }

    public void setOp(BinOp op) {
        this.op = op;
    }

    public ExprNode getArg1() {
        return arg1;
    }

    public void setArg1(ExprNode arg1) {
        this.arg1 = arg1;
    }

    public ExprNode getArg2() {
        return arg2;
    }

    public void setArg2(ExprNode arg2) {
        this.arg2 = arg2;
    }
}
