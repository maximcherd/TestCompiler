package ast;

import exceptions.SemanticException;
import semanticBase.IdentDesc;
import semanticBase.IdentScope;
import semanticBase.TypeDesc;
import semanticChecker.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Базовый абстрактный класс узла AST-дерева
 */
public abstract class AstNode {
    private Integer row = null;
    private Integer col = null;
    private TypeDesc nodeType = null;
    private IdentDesc nodeIdent = null;

    public AstNode() {
    }

    public AstNode(Integer row, Integer col) {
        this.row = row;
        this.col = col;
    }

    public AstNode(Integer row, Integer col, TypeDesc nodeType, IdentDesc nodeIdent) {
        this.row = row;
        this.col = col;
        this.nodeType = nodeType;
        this.nodeIdent = nodeIdent;
    }

    public List<AstNode> childs() {
        return new ArrayList<AstNode>();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String toStringFull() {
        String r = "";
        if (this.nodeIdent != null) {
            r = this.nodeIdent.toString();
        } else if (this.nodeType != null) {
            r = this.nodeType.toString();
        }
        return this.toString() + (!r.equals("") ? " : " + r : "");
    }

    public List<String> tree() {
        List<String> r = new ArrayList<>();
        r.add(toStringFull());
        List<AstNode> childs = this.childs();
        for (int i = 0; i < childs.size(); i++) {
            List<String> childTree = childs.get(i).tree();
            char ch0 = '├';
            char ch = '│';
            if (i == childs.size() - 1) {
                ch0 = '└';
                ch = ' ';
            }
            for (int j = 0; j < childTree.size(); j++) {
                r.add((j == 0 ? ch0 : ch) + " " + childTree.get(j));
            }
        }
        return r;
    }

    public SemanticException semanticError(String message) {
        return new SemanticException(message, this.row, this.col);
    }

    public void semanticCheck(SemanticChecker checker, IdentScope scope) throws SemanticException {
        checker.semanticCheck(this, scope);
    }


//    """Чтобы среда не "ругалась" в модуле msil
//    """
//    def msil_gen(self, generator) -> None:
//            generator.msil_gen(self)
//
//            """Чтобы среда не "ругалась" в модуле jbc
//    """
//    def jbc_gen(self, generator) -> None:
//            generator.jbc_gen(self)
//
//            """Чтобы среда не "ругалась" в модуле llvm
//    """
//    def llvm_gen(self, generator) -> None:
//            generator.llvm_gen(self)


    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public TypeDesc getNodeType() {
        return nodeType;
    }

    public void setNodeType(TypeDesc nodeType) {
        this.nodeType = nodeType;
    }

    public IdentDesc getNodeIdent() {
        return nodeIdent;
    }

    public void setNodeIdent(IdentDesc nodeIdent) {
        this.nodeIdent = nodeIdent;
    }
}
