package ast;

import java.util.List;

/**
 * Класс для группировки других узлов (вспомогательный, в синтаксисе нет соответствия)
 */
public class GroupNode extends AstNode {
    private String name;
    private List<AstNode> childs;

    public GroupNode(String name, List<AstNode> childs) {
        this.name = name;
        this.childs = childs;
    }

    public GroupNode(Integer row, Integer col, String name, List<AstNode> childs) {
        super(row, col);
        this.name = name;
        this.childs = childs;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public List<AstNode> childs() {
        return this.childs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AstNode> getChilds() {
        return childs;
    }

    public void setChilds(List<AstNode> childs) {
        this.childs = childs;
    }
}
