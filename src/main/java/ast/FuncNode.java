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

    public TypeNode getType() {
        return type;
    }

    public void setType(TypeNode type) {
        this.type = type;
    }

    public IdentNode getName() {
        return name;
    }

    public void setName(IdentNode name) {
        this.name = name;
    }

    public List<ParamNode> getParams() {
        return params;
    }

    public void setParams(List<ParamNode> params) {
        this.params = params;
    }

    public StmtNode getBody() {
        return body;
    }

    public void setBody(StmtNode body) {
        this.body = body;
    }
}
