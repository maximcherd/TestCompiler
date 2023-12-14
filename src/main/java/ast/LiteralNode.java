package ast;

public class LiteralNode extends ExprNode {
    String literal;

    public LiteralNode(String literal) {
        this.literal = literal;
    }

    public LiteralNode(String literal, Integer row, Integer col) {
        super(row, col);
        this.literal = literal;
    }

    @Override
    public String toString() {
        return this.literal;
    }
}
