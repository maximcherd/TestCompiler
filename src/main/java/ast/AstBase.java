package ast;

import semanticBase.IdentDesc;
import semanticBase.TypeDesc;


/**
 * Класс с базовыми константами для AST
 */
public class AstBase {
    public static StmtListNode EMPTY_STMT = new StmtListNode();
    public static IdentDesc EMPTY_IDENT = new IdentDesc("", TypeDesc.VOID);
}
