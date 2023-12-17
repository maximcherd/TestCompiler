package ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для представления в AST-дереве последовательности инструкций
 */
public class StmtListNode extends StmtNode {
    private List<StmtNode> stmts = new ArrayList<>();
    private boolean program = false;

    public StmtListNode() {
    }

    public StmtListNode(List<StmtNode> stmts) {
        this.stmts = stmts;
    }

    public StmtListNode(Integer row, Integer col, List<StmtNode> stmts) {
        super(row, col);
        this.stmts = stmts;
    }

    @Override
    public String toString() {
        return "...";
    }

    @Override
    public List<AstNode> childs() {
        return new ArrayList<>(this.stmts);
    }

    public List<StmtNode> getStmts() {
        return stmts;
    }

    public void setStmts(List<StmtNode> stmts) {
        this.stmts = stmts;
    }

    public boolean isProgram() {
        return program;
    }

    public void setProgram(boolean program) {
        this.program = program;
    }
}
