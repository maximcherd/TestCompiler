package ast;

import semanticBase.TypeDesc;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для представления в AST-дереве операций конвертации типов данных
 * (в языке программирования может быть как expression, так и statement)
 */
public class TypeConvertNode extends ExprNode {
    private ExprNode expr;
    private TypeDesc type;


    public TypeConvertNode(ExprNode expr, TypeDesc type) {
        this.expr = expr;
        this.type = type;
        super.setNodeType(type);
    }

    public TypeConvertNode(Integer row, Integer col, ExprNode expr, TypeDesc type) {
        super(row, col);
        this.expr = expr;
        this.type = type;
        super.setNodeType(type);
    }

    @Override
    public String toString() {
        return "convert";
    }

    @Override
    public List<AstNode> childs() {
        List<AstNode> astNodes = new ArrayList<>();
        List<AstNode> groupNodes = new ArrayList<>();
        groupNodes.add(this.expr);
        astNodes.add(new GroupNode(this.type.toString(), groupNodes));
        return astNodes;
    }

    public ExprNode getExpr() {
        return expr;
    }

    public void setExpr(ExprNode expr) {
        this.expr = expr;
    }

    public TypeDesc getType() {
        return type;
    }

    public void setType(TypeDesc type) {
        this.type = type;
    }
}
