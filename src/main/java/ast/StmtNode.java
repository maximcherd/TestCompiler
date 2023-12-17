package ast;

import semanticBase.IdentDesc;
import semanticBase.TypeDesc;

/**
 * Абстракный класс для деклараций или инструкций в AST-дереве
 */
public abstract class StmtNode extends ExprNode {
    public StmtNode() {
    }

    public StmtNode(Integer row, Integer col) {
        super(row, col);
    }

    public StmtNode(Integer row, Integer col, TypeDesc nodeType, IdentDesc nodeIdent) {
        super(row, col, nodeType, nodeIdent);
    }

    @Override
    public String toStringFull() {
        return this.toString();
    }
}
