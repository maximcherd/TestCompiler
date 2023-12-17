package ast;

import semanticBase.IdentDesc;
import semanticBase.TypeDesc;


public class AstBase {
    public static StmtListNode EMPTY_STMT = new StmtListNode();
    public static IdentDesc EMPTY_IDENT = new IdentDesc("", TypeDesc.VOID);
}
