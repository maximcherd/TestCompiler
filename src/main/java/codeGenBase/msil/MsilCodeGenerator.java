package codeGenBase.msil;

import ast.*;
import codeGenBase.CodeGenBase;
import codeGenBase.CodeGenerator;
import codeGenBase.CodeLabel;
import semanticBase.*;

import java.util.*;

import static codeGenBase.CodeGenBase.DEFAULT_TYPE_VALUES;
import static codeGenBase.CodeGenBase.findVarsDecls;

/**
 * Класс для генерации MSIL-кода
 */
public class MsilCodeGenerator extends CodeGenerator {
    private static String RUNTIME_CLASS_NAME = "CompilerDemo.Runtime";
    private static String PROGRAM_CLASS_NAME = "Program";

    private static HashMap<BaseType, String> MSIL_TYPE_NAMES = new HashMap<>();

    static {
        MSIL_TYPE_NAMES.put(BaseType.VOID, "void");
        MSIL_TYPE_NAMES.put(BaseType.INT, "int32");
        MSIL_TYPE_NAMES.put(BaseType.FLOAT, "float64");
        MSIL_TYPE_NAMES.put(BaseType.BOOL, "bool");
        MSIL_TYPE_NAMES.put(BaseType.STR, "string");
    }

    public void start() {
        this.add(".assembly program");
        this.add("{");
        this.add("}");
        this.add(String.format(".class public %s", PROGRAM_CLASS_NAME));
        this.add(".assembly program");
        this.add("{");
    }

    public void end() {
        this.add("}");
    }

    public void pushConst(BaseType type, Object value) {
        if (type.equals(BaseType.INT)) {
            this.add("ldc.i4", value);
        } else if (type.equals(BaseType.FLOAT)) {
            this.add("ldc.r8", value.toString());
        } else if (type.equals(BaseType.BOOL)) {
            this.add("ldc.i4", (Boolean) value ? 1 : 0);
        } else if (type.equals(BaseType.STR)) {
            this.add(String.format("ldstr %s", value));
        }
    }

    public void msilGen(AstNode node) {
        if (node instanceof LiteralNode) {
            this.msilGen((LiteralNode) node);
        } else if (node instanceof IdentNode) {
            this.msilGen((IdentNode) node);
        } else if (node instanceof AssignNode) {
            this.msilGen((AssignNode) node);
        } else if (node instanceof VarsNode) {
            this.msilGen((VarsNode) node);
        } else if (node instanceof BinOpNode) {
            this.msilGen((BinOpNode) node);
        } else if (node instanceof TypeConvertNode) {
            this.msilGen((TypeConvertNode) node);
        } else if (node instanceof CallNode) {
            this.msilGen((CallNode) node);
        } else if (node instanceof ReturnNode) {
            this.msilGen((ReturnNode) node);
        } else if (node instanceof IfNode) {
            this.msilGen((IfNode) node);
        } else if (node instanceof WhileNode) {
            this.msilGen((WhileNode) node);
        } else if (node instanceof ForNode) {
            this.msilGen((ForNode) node);
        } else if (node instanceof FuncNode) {
            this.msilGen((FuncNode) node);
        } else if (node instanceof StmtListNode) {
            this.msilGen((StmtListNode) node);
        }
    }

    public void msilGen(LiteralNode node) {
        this.pushConst(node.getNodeType().getBaseType(), node.getValue());
    }

    public void msilGen(IdentNode node) {
        if (node.getNodeIdent().getScope().equals(ScopeType.LOCAL)) {
            this.add("ldloc", node.getNodeIdent().getIndex());
        } else if (node.getNodeIdent().getScope().equals(ScopeType.PARAM)) {
            this.add("ldarg", node.getNodeIdent().getIndex());
        } else if (node.getNodeIdent().getScope().equals(ScopeType.GLOBAL) ||
                node.getNodeIdent().getScope().equals(ScopeType.GLOBAL_LOCAL)) {
            this.add(String.format("ldsfld %s %s::_gv%d",
                    MSIL_TYPE_NAMES.get(node.getNodeIdent().getType().getBaseType()), PROGRAM_CLASS_NAME, node.getNodeIdent().getIndex()));
        }
    }

    public void msilGen(AssignNode node) {
        node.getVal().msilGen(this);
        IdentNode var = node.getVar();
        if (var.getNodeIdent().getScope().equals(ScopeType.LOCAL)) {
            this.add("stloc", var.getNodeIdent().getIndex());
        } else if (var.getNodeIdent().getScope().equals(ScopeType.PARAM)) {
            this.add("starg", var.getNodeIdent().getIndex());
        } else if (var.getNodeIdent().getScope().equals(ScopeType.GLOBAL) ||
                var.getNodeIdent().getScope().equals(ScopeType.GLOBAL_LOCAL)) {
            this.add(String.format("stsfld %s Program::_gv%d",
                    MSIL_TYPE_NAMES.get(var.getNodeIdent().getType().getBaseType()), var.getNodeIdent().getIndex()));
        }
    }

    public void msilGen(VarsNode node) {
        for (ExprNode var : node.getVars()) {
            if (var instanceof AssignNode) {
                var.msilGen(this);
            }
        }
    }

    public void msilGen(BinOpNode node) {
        node.getArg1().msilGen(this);
        node.getArg2().msilGen(this);
        if (node.getOp().equals(BinOp.NEQUALS)) {
            if (node.getArg1().getNodeType().equals(TypeDesc.STR)) {
                this.add("call bool [mscorlib]System.String::op_Inequality(string, string)");
            } else {
                this.add("ceq");
                this.add("ldc.i4.0");
                this.add("ceq");
            }
        }
        if (node.getOp().equals(BinOp.EQUALS)) {
            if (node.getArg1().getNodeType().equals(TypeDesc.STR)) {
                this.add("call bool [mscorlib]System.String::op_Equality(string, string)");
            } else {
                this.add("ceq");
            }
        } else if (node.getOp().equals(BinOp.GT)) {
            if (node.getArg1().getNodeType().equals(TypeDesc.STR)) {
                this.add(String.format("call %s class %s::compare(%s, %s)", MSIL_TYPE_NAMES.get(BaseType.INT),
                        RUNTIME_CLASS_NAME, MSIL_TYPE_NAMES.get(BaseType.STR), MSIL_TYPE_NAMES.get(BaseType.STR)));
                this.add("ldc.i4.0");
                this.add("cgt");
            } else {
                this.add("cgt");
            }
        } else if (node.getOp().equals(BinOp.LT)) {
            if (node.getArg1().getNodeType().equals(TypeDesc.STR)) {
                this.add(String.format("call %s class %s::compare(%s, %s)", MSIL_TYPE_NAMES.get(BaseType.INT),
                        RUNTIME_CLASS_NAME, MSIL_TYPE_NAMES.get(BaseType.STR), MSIL_TYPE_NAMES.get(BaseType.STR)));
                this.add("ldc.i4.0");
                this.add("clt");
            } else {
                this.add("clt");
            }
        } else if (node.getOp().equals(BinOp.GE)) {
            if (node.getArg1().getNodeType().equals(TypeDesc.STR)) {
                this.add(String.format("call %s class %s::compare(%s, %s)", MSIL_TYPE_NAMES.get(BaseType.INT),
                        RUNTIME_CLASS_NAME, MSIL_TYPE_NAMES.get(BaseType.STR), MSIL_TYPE_NAMES.get(BaseType.STR)));
                this.add("ldc.i4", "-1");
                this.add("cgt");
            } else {
                this.add("clt");
                this.add("ldc.i4.0");
                this.add("ceq");
            }
        } else if (node.getOp().equals(BinOp.LE)) {
            if (node.getArg1().getNodeType().equals(TypeDesc.STR)) {
                this.add(String.format("call %s class %s::compare(%s, %s)", MSIL_TYPE_NAMES.get(BaseType.INT),
                        RUNTIME_CLASS_NAME, MSIL_TYPE_NAMES.get(BaseType.STR), MSIL_TYPE_NAMES.get(BaseType.STR)));
                this.add("ldc.i4.1");
                this.add("clt");
            } else {
                this.add("cgt");
                this.add("ldc.i4.0");
                this.add("ceq");
            }
        } else if (node.getOp().equals(BinOp.ADD)) {
            if (node.getArg1().getNodeType().equals(TypeDesc.STR)) {
                this.add(String.format("call %s class %s::concat(%s, %s)", MSIL_TYPE_NAMES.get(BaseType.INT),
                        RUNTIME_CLASS_NAME, MSIL_TYPE_NAMES.get(BaseType.STR), MSIL_TYPE_NAMES.get(BaseType.STR)));
            } else {
                this.add("add");
            }
        } else if (node.getOp().equals(BinOp.SUB)) {
            this.add("sub");
        } else if (node.getOp().equals(BinOp.MUL)) {
            this.add("mul");
        } else if (node.getOp().equals(BinOp.DIV)) {
            this.add("div");
        } else if (node.getOp().equals(BinOp.MOD)) {
            this.add("rem");
        } else if (node.getOp().equals(BinOp.LOGICAL_AND)) {
            this.add("and");
        } else if (node.getOp().equals(BinOp.LOGICAL_OR)) {
            this.add("or");
        } else if (node.getOp().equals(BinOp.BIT_AND)) {
            this.add("and");
        } else if (node.getOp().equals(BinOp.BIT_OR)) {
            this.add("or");
        }
    }

    public void msilGen(TypeConvertNode node) {
        node.getExpr().msilGen(this);
        if (node.getNodeType().getBaseType().equals(BaseType.FLOAT)
                && node.getExpr().getNodeType().getBaseType().equals(BaseType.INT)) {
            this.add("conv.r8");
        } else if (node.getNodeType().getBaseType().equals(BaseType.BOOL)
                && node.getExpr().getNodeType().getBaseType().equals(BaseType.INT)) {
            this.add("ldc.i4.0");
            this.add("ceq");
            this.add("ldc.i4.0");
            this.add("ceq");
        } else {
            this.add(String.format("call %s class %s::convert(%s)", MSIL_TYPE_NAMES.get(node.getNodeType().getBaseType()),
                    RUNTIME_CLASS_NAME, MSIL_TYPE_NAMES.get(node.getExpr().getNodeType().getBaseType())));
        }
    }

    public void msilGen(CallNode node) {
        StringJoiner paramTypes = new StringJoiner(", ");
        for (ExprNode param : node.getParams()) {
            param.msilGen(this);
            paramTypes.add(MSIL_TYPE_NAMES.get(param.getNodeType().getBaseType()));
        }
        String className = node.getFunc().getNodeIdent().isBuiltIn() ? RUNTIME_CLASS_NAME : PROGRAM_CLASS_NAME;
        this.add(String.format("call %s class %s::%s(%s)", MSIL_TYPE_NAMES.get(node.getNodeType().getBaseType()),
                className, node.getFunc().getName(), paramTypes.toString()));
    }

    public void msilGen(ReturnNode node) {
        node.getVal().msilGen(this);
        this.add("ret");
    }

    public void msilGen(IfNode node) {
        CodeLabel elseLabel = new CodeLabel();
        CodeLabel endLabel = new CodeLabel();
        node.getCond().msilGen(this);
        this.add("brfalse", elseLabel);
        node.getThenStmt().msilGen(this);
        this.add("br", endLabel);
        this.add(elseLabel);
        if (node.getElseStmt() != null) {
            node.getElseStmt().msilGen(this);
        }
        this.add(endLabel);
    }

    public void msilGen(WhileNode node) {
        CodeLabel startLabel = new CodeLabel();
        CodeLabel endLabel = new CodeLabel();
        this.add(startLabel);
        node.getCond().msilGen(this);
        this.add("brfalse", endLabel);
        node.getBody().msilGen(this);
        this.add("br", startLabel);
        this.add(endLabel);
    }

    public void msilGen(ForNode node) {
        CodeLabel startLabel = new CodeLabel();
        CodeLabel endLabel = new CodeLabel();
        node.getInit().msilGen(this);
        this.add(startLabel);
        node.getCond().msilGen(this);
        this.add("brfalse", endLabel);
        node.getBody().msilGen(this);
        node.getStep().msilGen(this);
        this.add("br", startLabel);
        this.add(endLabel);
    }

    public void msilGen(FuncNode node) {
        StringJoiner params = new StringJoiner(", ");
        for (ParamNode param : node.getParams()) {
            params.add(String.format("%s %s",
                    MSIL_TYPE_NAMES.get(param.getType().getType().getBaseType()), param.getName().getName()));
        }
        this.add(String.format(".method public static %s %s(%s) cil managed {",
                MSIL_TYPE_NAMES.get(node.getType().getType().getBaseType()), node.getName().toString(), params));
        List<VarsNode> localVarsDecls = CodeGenBase.findVarsDecls(node);
        StringBuilder decl = new StringBuilder(".locals init (");
        int count = 0;
        for (VarsNode varsNode : localVarsDecls) {
            for (ExprNode var : varsNode.getVars()) {
                if (var instanceof AssignNode) {
                    var = ((AssignNode) var).getVar();
                }
                if (var.getNodeIdent().getScope().equals(ScopeType.LOCAL)) {
                    if (count > 0) {
                        decl.append(", ");
                    }
                    decl.append(String.format("%s _v%d",
                            MSIL_TYPE_NAMES.get(var.getNodeType().getBaseType()), var.getNodeIdent().getIndex()));
                    count++;
                }
            }
        }
        decl.append(")");
        if (count > 0) {
            this.add(decl.toString());
        }
        node.getBody().msilGen(this);
        if (!(node.getBody() instanceof ReturnNode || node.getBody().childs().size() > 0
                && node.getBody().childs().get(node.getBody().childs().size() - 1) instanceof ReturnNode)) {
            if (!node.getType().getType().getBaseType().equals(BaseType.VOID)) {
                this.pushConst(node.getType().getType().getBaseType(), DEFAULT_TYPE_VALUES.get(node.getType().getType().getBaseType()));
            }
            this.add("ret");
        }
        this.add("}");
    }

    public void msilGen(StmtListNode node) {
        for (StmtNode stmt : node.getStmts()) {
            stmt.msilGen(this);
        }
    }

    public void genProgram(StmtListNode prog) {
        this.start();
        List<VarsNode> globalVarsDecls = findVarsDecls(prog);
        for (VarsNode node : globalVarsDecls) {
            for (ExprNode var : node.getVars()) {
                if (var instanceof AssignNode) {
                    var = ((AssignNode) var).getVar();
                }
                if (var.getNodeIdent().getScope().equals(ScopeType.GLOBAL)
                        || var.getNodeIdent().getScope().equals(ScopeType.GLOBAL_LOCAL)) {
                    this.add(String.format(".field public static %s _gv%d",
                            MSIL_TYPE_NAMES.get(var.getNodeType().getBaseType()), var.getNodeIdent().getIndex()));
                }
            }
        }
        for (StmtNode stmt : prog.getStmts()) {
            if (stmt instanceof FuncNode) {
                stmt.msilGen(this);
            }
        }
        this.add("");
        this.add(".method public static void Main()");
        this.add("{");
        this.add(".entrypoint");
        for (StmtNode stmt : prog.getStmts()) {
            if (!(stmt instanceof FuncNode)) {
                stmt.msilGen(this);
            }
        }
        this.add("ret");
        this.add("}");
        this.end();
    }
}
