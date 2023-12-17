package ast;

/**
 * Класс для представления в AST-дереве литералов (числа, строки, логическое значение)
 */
public class LiteralNode extends ExprNode {
    private String literal;
    private Object value;


    public LiteralNode(String literal) {
        initNode(literal);
    }

    public LiteralNode(String literal, Integer row, Integer col) {
        super(row, col);
        initNode(literal);
    }

    private void initNode(String literal) {
        this.literal = literal;
        if (literal.equals("true") || literal.equals("false")) {
            this.value = Boolean.valueOf(literal);
        } else if (literal.contains("\"")) {
            this.value = literal;
        } else if (literal.contains(".")) {
            this.value = Float.valueOf(literal);
        } else {
            this.value = Integer.valueOf(literal);
        }
    }

    @Override
    public String toString() {
        return this.literal;
    }

    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
