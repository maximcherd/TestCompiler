package codeGenBase;

import ast.AstNode;
import ast.VarsNode;
import semanticBase.BaseType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CodeGenBase {
    public static HashMap<BaseType, Object> DEFAULT_TYPE_VALUES = new HashMap<>();

    static {
        DEFAULT_TYPE_VALUES.put(BaseType.INT, 0);
        DEFAULT_TYPE_VALUES.put(BaseType.FLOAT, 0.0);
        DEFAULT_TYPE_VALUES.put(BaseType.BOOL, false);
        DEFAULT_TYPE_VALUES.put(BaseType.STR, "");
    }

    public static List<VarsNode> findVarsDecls(AstNode node) {
        List<VarsNode> varsNodes = new ArrayList<>();
        find(node, varsNodes);
        return varsNodes;
    }

    private static void find(AstNode node, List<VarsNode> varsNodes) {
        for (AstNode n : node.childs()) {
            if (n instanceof VarsNode) {
                varsNodes.add((VarsNode) n);
            } else {
                find(n, varsNodes);
            }
        }
    }
}
