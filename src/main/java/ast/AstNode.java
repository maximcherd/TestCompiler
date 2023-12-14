package ast;

import semanticBase.IdentDesc;
import semanticBase.TypeDesc;

import java.util.ArrayList;
import java.util.List;

public abstract class AstNode {
    protected Integer row = null;
    protected Integer col = null;
    protected TypeDesc nodeType = null;
    protected IdentDesc nodeIdent = null;

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
        return "AstNodeAbst{}";
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
}
