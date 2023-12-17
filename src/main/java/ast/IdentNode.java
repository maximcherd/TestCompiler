package ast;

/**
 * Класс для представления в AST-дереве идентификаторов
 */
public class IdentNode extends ExprNode {
    private String name;

    public IdentNode(String name) {
        this.name = name;
    }

    public IdentNode(Integer row, Integer col, String name) {
        super(row, col);
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
