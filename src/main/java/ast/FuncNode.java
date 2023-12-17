package ast;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для представления в AST-дереве объявления функции
 */
public class FuncNode extends StmtNode {
    private TypeNode type;
    private IdentNode name;
    private List<ParamNode> params;
    private StmtNode body;

    public FuncNode(TypeNode type, IdentNode name, List<ParamNode> params, StmtNode body) {
        this.type = type;
        this.name = name;
        this.params = params;
        this.body = body;
    }

    public FuncNode(Integer row, Integer col, TypeNode type, IdentNode name, List<ParamNode> params, StmtNode body) {
        super(row, col);
        this.type = type;
        this.name = name;
        this.params = params;
        this.body = body;
    }

    @Override
    public String toString() {
        return "function";
    }

    @Override
    public List<AstNode> childs() {
        List<AstNode> astNodes = new ArrayList<>();
        List<AstNode> groupNodes = new ArrayList<>();
        groupNodes.add(this.name);
        astNodes.add(new GroupNode(this.type.toString(), groupNodes));
        astNodes.add(new GroupNode("params", new ArrayList<>(this.params)));
        astNodes.add(this.body);
        return astNodes;
    }
}
