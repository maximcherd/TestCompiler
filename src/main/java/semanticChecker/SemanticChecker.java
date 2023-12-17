package semanticChecker;

import ast.*;
import exceptions.SemanticException;
import parser.ParseException;
import parser.Parser;
import semanticBase.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ast.AstBase.EMPTY_IDENT;
import static ast.AstBase.EMPTY_STMT;
import static semanticBase.SemanticBase.*;

public class SemanticChecker {
    public static String BUILT_IN_OBJECTS =
            "string read() { }" +
                    "void print(string p0) { }" +
                    "void println(string p0) { }" +
                    "int to_int(string p0) { }" +
                    "float to_float(string p0) { }";

    public void semanticCheck(AstNode node, IdentScope scope) throws SemanticException {
        if (node instanceof LiteralNode) {
            semanticCheck((LiteralNode) node, scope);
        } else if (node instanceof TypeNode) {
            semanticCheck((TypeNode) node, scope);
        } else if (node instanceof IdentNode) {
            semanticCheck((IdentNode) node, scope);
        } else if (node instanceof BinOpNode) {
            semanticCheck((BinOpNode) node, scope);
        } else if (node instanceof CallNode) {
            semanticCheck((CallNode) node, scope);
        } else if (node instanceof AssignNode) {
            semanticCheck((AssignNode) node, scope);
        } else if (node instanceof VarsNode) {
            semanticCheck((VarsNode) node, scope);
        } else if (node instanceof ReturnNode) {
            semanticCheck((ReturnNode) node, scope);
        } else if (node instanceof IfNode) {
            semanticCheck((IfNode) node, scope);
        } else if (node instanceof WhileNode) {
            semanticCheck((WhileNode) node, scope);
        } else if (node instanceof ForNode) {
            semanticCheck((ForNode) node, scope);
        } else if (node instanceof ParamNode) {
            semanticCheck((ParamNode) node, scope);
        } else if (node instanceof FuncNode) {
            semanticCheck((FuncNode) node, scope);
        } else if (node instanceof StmtListNode) {
            semanticCheck((StmtListNode) node, scope);
        }
    }

    public void semanticCheck(LiteralNode node, IdentScope scope) throws SemanticException {
        if (node.getValue() instanceof Boolean) {
            node.setNodeType(TypeDesc.BOOL);
        } else if (node.getValue() instanceof Integer) {
            node.setNodeType(TypeDesc.INT);
        } else if (node.getValue() instanceof Float) {
            node.setNodeType(TypeDesc.FLOAT);
        } else if (node.getValue() instanceof String) {
            node.setNodeType(TypeDesc.STR);
        } else {
            throw node.semanticError(String.format("Неизвестный тип %s для %s",
                    node.getValue().getClass().toString(), node.getValue().toString()));
        }
    }

    public void semanticCheck(IdentNode node, IdentScope scope) throws SemanticException {
        IdentDesc ident = scope.getIdent(node.getName());
        if (ident == null) {
            throw node.semanticError(String.format("Идентификатор %s не найден", node.getName()));
        }
        node.setNodeType(ident.getType());
        node.setNodeIdent(ident);
    }

    public void semanticCheck(TypeNode node, IdentScope scope) throws SemanticException {
        if (node.getType() == null) {
            throw node.semanticError(String.format("Неизвестный тип %s", node.getName()));
        }
    }

    public void semanticCheck(BinOpNode node, IdentScope scope) throws SemanticException {
        node.getArg1().semanticCheck(this, scope);
        node.getArg2().semanticCheck(this, scope);

        if (node.getArg1().getNodeType().isSimple() || node.getArg2().getNodeType().isSimple()) {
            HashMap<ArrayList<BaseType>, BaseType> compatibility = BIN_OP_TYPE_COMPATIBILITY.get(node.getOp());
            ArrayList<BaseType> args_types = new ArrayList<>();
            args_types.add(node.getArg1().getNodeType().getBaseType());
            args_types.add(node.getArg2().getNodeType().getBaseType());
            if (compatibility.containsKey(args_types)) {
                node.setNodeType(TypeDesc.fromBaseType(compatibility.get(args_types)));
                return;
            }
            if (TYPE_CONVERTIBILITY.containsKey(node.getArg2().getNodeType().getBaseType())) {
                for (BaseType arg2_type : TYPE_CONVERTIBILITY.get(node.getArg2().getNodeType().getBaseType())) {
                    args_types = new ArrayList<>();
                    args_types.add(node.getArg1().getNodeType().getBaseType());
                    args_types.add(arg2_type);
                    if (compatibility.containsKey(args_types)) {
                        node.setArg2(typeConvert(node.getArg2(), TypeDesc.fromBaseType(arg2_type)));
                        node.setNodeType(TypeDesc.fromBaseType(compatibility.get(args_types)));
                        return;
                    }
                }
            }
            if (TYPE_CONVERTIBILITY.containsKey(node.getArg1().getNodeType().getBaseType())) {
                for (BaseType arg1_type : TYPE_CONVERTIBILITY.get(node.getArg1().getNodeType().getBaseType())) {
                    args_types = new ArrayList<>();
                    args_types.add(arg1_type);
                    args_types.add(node.getArg2().getNodeType().getBaseType());
                    if (compatibility.containsKey(args_types)) {
                        node.setArg1(typeConvert(node.getArg1(), TypeDesc.fromBaseType(arg1_type)));
                        node.setNodeType(TypeDesc.fromBaseType(compatibility.get(args_types)));
                        return;
                    }
                }
            }
        }
        throw node.semanticError(String.format("Оператор %s не применим к типам (%s, %s)",
                node.getOp().toString(), node.getArg1().getNodeType().toString(), node.getArg2().getNodeType().toString()));
    }

    public void semanticCheck(CallNode node, IdentScope scope) throws SemanticException {
        IdentDesc func = scope.getIdent(node.getFunc().getName());
        if (func == null) {
            throw node.semanticError(String.format("Функция %s не найдена", node.getFunc().getName()));
        }
        if (!func.getType().func()) {
            throw node.semanticError(String.format("Идентификатор %s не является функцией", func.getName()));
        }
        if (func.getType().getParams().size() != node.getParams().size()) {
            throw node.semanticError(String.format("Кол-во аргументов %s не совпадает (ожидалось %d, передано %d)",
                    func.getName(), func.getType().getParams().size(), node.getParams().size()));
        }
        List<ExprNode> params = new ArrayList<>();
        boolean error = false;
        StringBuilder declParamsStr = new StringBuilder();
        StringBuilder factParamsStr = new StringBuilder();
        for (int i = 0; i < node.getParams().size(); i++) {
            ExprNode param = node.getParams().get(i);
            param.semanticCheck(this, scope);
            if (declParamsStr.length() > 0) {
                declParamsStr.append(", ");
            }
            declParamsStr.append(func.getType().getParams().get(i).toString());
            if (factParamsStr.length() > 0) {
                factParamsStr.append(", ");
            }
            factParamsStr.append(param.getNodeType().toString());
            try {
                params.add(typeConvert(param, func.getType().getParams().get(i)));
            } catch (Exception e) {
                error = true;
            }
        }
        if (error) {
            throw node.semanticError(String.format("Фактические типы (%s) аргументов функции %s не совпадают" +
                            "с формальными (%s) и не приводимы",
                    factParamsStr, func.getName(), declParamsStr));
        } else {
            node.setParams(params);
            node.getFunc().setNodeType(func.getType());
            node.getFunc().setNodeIdent(func);
            node.setNodeType(func.getType().getReturnType());
        }
    }

    public void semanticCheck(AssignNode node, IdentScope scope) throws SemanticException {
        node.getVar().semanticCheck(this, scope);
        node.getVal().semanticCheck(this, scope);
        node.setVal(typeConvert(node.getVal(), node.getVar().getNodeType(), node, "присваемое значение"));
        node.setNodeType(node.getVar().getNodeType());
    }

    public void semanticCheck(VarsNode node, IdentScope scope) throws SemanticException {
        node.getType().semanticCheck(this, scope);
        for (ExprNode var : node.getVars()) {
            IdentNode varNode = var instanceof AssignNode ? ((AssignNode) var).getVar() : (IdentNode) var;
            try {
                scope.addIdent(new IdentDesc(varNode.getName(), node.getType().getType()));
            } catch (SemanticException e) {
                throw varNode.semanticError(e.getMessage());
            }
            var.semanticCheck(this, scope);
        }
        node.setNodeType(TypeDesc.VOID);
    }

    public void semanticCheck(ReturnNode node, IdentScope scope) throws SemanticException {
        node.getVal().semanticCheck(this, new IdentScope(scope));
        IdentScope func = scope.currFunc();
        if (func == null) {
            throw node.semanticError("Оператор return применим только к функции");
        }
        node.setVal(typeConvert(node.getVal(), func.getFunc().getType().getReturnType(), node, "возвращаемое значение"));
        node.setNodeType(TypeDesc.VOID);
    }

    public void semanticCheck(IfNode node, IdentScope scope) throws SemanticException {
        node.getCond().semanticCheck(this, scope);
        node.setCond(typeConvert(node.getCond(), TypeDesc.BOOL, null, "условие"));
        node.getThenStmt().semanticCheck(this, new IdentScope(scope));
        if (node.getElseStmt() != null) {
            node.getElseStmt().semanticCheck(this, new IdentScope(scope));
        }
        node.setNodeType(TypeDesc.VOID);
    }

    public void semanticCheck(WhileNode node, IdentScope scope) throws SemanticException {
        node.getCond().semanticCheck(this, scope);
        node.setCond(typeConvert(node.getCond(), TypeDesc.BOOL, null, "условие"));
        node.getBody().semanticCheck(this, new IdentScope(scope));
        node.setNodeType(TypeDesc.VOID);
    }

    public void semanticCheck(ForNode node, IdentScope scope) throws SemanticException {
        scope = new IdentScope(scope);
        node.getInit().semanticCheck(this, scope);
        if (node.getCond() == EMPTY_STMT) {
            node.setCond(new LiteralNode("true"));
        }
        node.getCond().semanticCheck(this, scope);
        node.setCond(typeConvert(node.getCond(), TypeDesc.BOOL, null, "условие"));
        node.getStep().semanticCheck(this, scope);
        node.getBody().semanticCheck(this, new IdentScope(scope));
        node.setNodeType(TypeDesc.VOID);
    }

    public void semanticCheck(ParamNode node, IdentScope scope) throws SemanticException {
        node.getType().semanticCheck(this, scope);
        node.getName().setNodeType(node.getType().getType());
        try {
            node.getName().setNodeIdent(scope.addIdent(
                    new IdentDesc(node.getName().getName(), node.getType().getType(), ScopeType.PARAM, 0)));
        } catch (SemanticException e) {
            throw node.getName().semanticError(String.format("Параметр %s уже объявлен", node.getName().getName()));
        }
        node.setNodeType(TypeDesc.VOID);
    }

    public void semanticCheck(FuncNode node, IdentScope scope) throws SemanticException {
        if (scope.currFunc() != null) {
            throw node.semanticError(String.format("Объявление функции (%s) внутри другой функции не поддерживается",
                    node.getName().getName()));
        }
        IdentScope parentScope = scope;
        node.getType().semanticCheck(this, scope);
        scope = new IdentScope(scope);
        scope.setFunc(EMPTY_IDENT);
        List<TypeDesc> params = new ArrayList<>();
        for (ParamNode param : node.getParams()) {
            param.semanticCheck(this, scope);
            param.setNodeIdent(scope.getIdent(param.getName().getName()));
            params.add(param.getType().getType());
        }
        TypeDesc type_ = new TypeDesc(null, node.getType().getType(), params);
        IdentDesc funcIdent = new IdentDesc(node.getName().getName(), type_);
        scope.setFunc(funcIdent);
        node.getName().setNodeType(type_);
        try {
            node.getName().setNodeIdent(parentScope.currGlobal().addIdent(funcIdent));
        } catch (SemanticException e) {
            throw node.getName().semanticError(String.format("Повторное объявление функции %s", node.getName().getName()));
        }
        node.getBody().semanticCheck(this, scope);
        node.setNodeType(TypeDesc.VOID);
    }

    public void semanticCheck(StmtListNode node, IdentScope scope) throws SemanticException {
        if (!node.isProgram()) {
            scope = new IdentScope(scope);
        }
        for (StmtNode stmt : node.getStmts()) {
            stmt.semanticCheck(this, scope);
        }
        node.setNodeType(TypeDesc.VOID);
    }

    public static IdentScope prepareGlobalScope() throws SemanticException, ParseException {
        StmtListNode prog = Parser.parse(BUILT_IN_OBJECTS);
        SemanticChecker checker = new SemanticChecker();
        IdentScope scope = new IdentScope();
        checker.semanticCheck(prog, scope);
        for (Map.Entry<String, IdentDesc> entry : scope.getIdents().entrySet()) {
            entry.getValue().setBuiltIn(true);
        }
        scope.setVarIndex(0);
        return scope;
    }
}
