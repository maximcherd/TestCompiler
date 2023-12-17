package ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для представления в AST-дереве вызова функций
 * (в языке программирования может быть как expression, так и statement)
 */
public class CallNode extends StmtNode {
    private IdentNode func;
    private List<ExprNode> params;

    public CallNode(IdentNode func, List<ExprNode> params) {
        this.func = func;
        this.params = params;
    }

    public CallNode(Integer row, Integer col, IdentNode func, List<ExprNode> params) {
        super(row, col);
        this.func = func;
        this.params = params;
    }

    @Override
    public String toString() {
        return "call";
    }

    @Override
    public List<AstNode> childs() {
        List<AstNode> astNodes = new ArrayList<>();
        astNodes.add(this.func);
        astNodes.addAll(this.params);
        return astNodes;
    }

    public IdentNode getFunc() {
        return func;
    }

    public void setFunc(IdentNode func) {
        this.func = func;
    }

    public List<ExprNode> getParams() {
        return params;
    }

    public void setParams(List<ExprNode> params) {
        this.params = params;
    }
}
