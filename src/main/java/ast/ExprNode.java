package ast;

import semanticBase.IdentDesc;
import semanticBase.TypeDesc;

public class ExprNode extends AstNode {
    public ExprNode() {
    }

    public ExprNode(Integer row, Integer col) {
        super(row, col);
    }

    public ExprNode(Integer row, Integer col, TypeDesc nodeType, IdentDesc nodeIdent) {
        super(row, col, nodeType, nodeIdent);
    }


}
