package ast;

import exceptions.SemanticException;
import semanticBase.IdentDesc;
import semanticBase.TypeDesc;
import semanticBase.SemanticBase;


public class AstBase {
    public static StmtListNode EMPTY_STMT = new StmtListNode();
    public static IdentDesc EMPTY_IDENT = new IdentDesc("", TypeDesc.VOID);

    public static ExprNode typeConvert(ExprNode expr, TypeDesc type, AstNode exceptNode, String comment) throws SemanticException {
        if (expr.getNodeType() == null) {
            throw exceptNode.semanticError("Тип выражения не определен");
        }
        if (expr.getNodeType() == type) {
            return expr;
        }
        if (expr.getNodeType().isSimple() && type.isSimple() &&
                SemanticBase.TYPE_CONVERTIBILITY.containsKey(expr.getNodeType().getBaseType()) &&
                SemanticBase.TYPE_CONVERTIBILITY.get(expr.getNodeType().getBaseType()).contains(type.getBaseType())) {
            return new TypeConvertNode(expr, type);
        } else {
            throw (exceptNode != null ? exceptNode : expr).semanticError(
                    String.format("Тип %s%s не конвертируется в %s",
                            expr.getNodeType().toString(),
                            comment != null && !comment.isBlank() ? String.format(" (%s)", comment) : "",
                            type.toString()
                    )
            );
        }
    }

}
