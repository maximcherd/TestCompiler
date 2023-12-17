package ast;

import java.util.List;

/**
 * Класс для группировки других узлов (вспомогательный, в синтаксисе нет соответствия)
 */
public class GroupNode extends AstNode {
    private String name;
    private List<AstNode> _childs;

    public GroupNode(String name, List<AstNode> childs) {
        this.name = name;
        this._childs = childs;
    }

    public GroupNode(Integer row, Integer col, String name, List<AstNode> _childs) {
        super(row, col);
        this.name = name;
        this._childs = _childs;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public List<AstNode> childs() {
        return this._childs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AstNode> get_childs() {
        return _childs;
    }

    public void set_childs(List<AstNode> _childs) {
        this._childs = _childs;
    }
}
