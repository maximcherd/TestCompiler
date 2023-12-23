package semanticBase;

import ast.AstNode;
import ast.ExprNode;
import ast.StmtListNode;
import ast.TypeConvertNode;
import exceptions.SemanticException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Класс базовых методов и констант для семантического анализа
 */
public class SemanticBase {
    public static BaseType VOID = BaseType.VOID;
    public static BaseType INT = BaseType.INT;
    public static BaseType FLOAT = BaseType.FLOAT;
    public static BaseType BOOL = BaseType.BOOL;
    public static BaseType STR = BaseType.STR;

    public static HashMap<BaseType, ArrayList<BaseType>> TYPE_CONVERTIBILITY;

    static {
        TYPE_CONVERTIBILITY = new HashMap<>();
        TYPE_CONVERTIBILITY.put(INT, new ArrayList<>(Arrays.asList(FLOAT, BOOL, STR)));
        TYPE_CONVERTIBILITY.put(FLOAT, new ArrayList<>(Arrays.asList(STR)));
        TYPE_CONVERTIBILITY.put(BOOL, new ArrayList<>(Arrays.asList(STR)));
    }

    public static HashMap<BinOp, HashMap<ArrayList<BaseType>, BaseType>> BIN_OP_TYPE_COMPATIBILITY;

    static {
        BIN_OP_TYPE_COMPATIBILITY = new HashMap<>();
        HashMap<ArrayList<BaseType>, BaseType> temp;

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.ADD, temp);
        temp.put(new ArrayList<>(Arrays.asList(INT, INT)), INT);
        temp.put(new ArrayList<>(Arrays.asList(FLOAT, FLOAT)), FLOAT);
        temp.put(new ArrayList<>(Arrays.asList(STR, STR)), STR);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.SUB, temp);
        temp.put(new ArrayList<>(Arrays.asList(INT, INT)), INT);
        temp.put(new ArrayList<>(Arrays.asList(FLOAT, FLOAT)), FLOAT);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.MUL, temp);
        temp.put(new ArrayList<>(Arrays.asList(INT, INT)), INT);
        temp.put(new ArrayList<>(Arrays.asList(FLOAT, FLOAT)), FLOAT);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.DIV, temp);
        temp.put(new ArrayList<>(Arrays.asList(INT, INT)), INT);
        temp.put(new ArrayList<>(Arrays.asList(FLOAT, FLOAT)), FLOAT);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.MOD, temp);
        temp.put(new ArrayList<>(Arrays.asList(INT, INT)), INT);
        temp.put(new ArrayList<>(Arrays.asList(FLOAT, FLOAT)), FLOAT);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.GT, temp);
        temp.put(new ArrayList<>(Arrays.asList(INT, INT)), BOOL);
        temp.put(new ArrayList<>(Arrays.asList(FLOAT, FLOAT)), BOOL);
        temp.put(new ArrayList<>(Arrays.asList(STR, STR)), BOOL);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.LT, temp);
        temp.put(new ArrayList<>(Arrays.asList(INT, INT)), BOOL);
        temp.put(new ArrayList<>(Arrays.asList(FLOAT, FLOAT)), BOOL);
        temp.put(new ArrayList<>(Arrays.asList(STR, STR)), BOOL);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.GE, temp);
        temp.put(new ArrayList<>(Arrays.asList(INT, INT)), BOOL);
        temp.put(new ArrayList<>(Arrays.asList(FLOAT, FLOAT)), BOOL);
        temp.put(new ArrayList<>(Arrays.asList(STR, STR)), BOOL);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.LE, temp);
        temp.put(new ArrayList<>(Arrays.asList(INT, INT)), BOOL);
        temp.put(new ArrayList<>(Arrays.asList(FLOAT, FLOAT)), BOOL);
        temp.put(new ArrayList<>(Arrays.asList(STR, STR)), BOOL);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.EQUALS, temp);
        temp.put(new ArrayList<>(Arrays.asList(INT, INT)), BOOL);
        temp.put(new ArrayList<>(Arrays.asList(FLOAT, FLOAT)), BOOL);
        temp.put(new ArrayList<>(Arrays.asList(STR, STR)), BOOL);
        temp.put(new ArrayList<>(Arrays.asList(BOOL, BOOL)), BOOL);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.NEQUALS, temp);
        temp.put(new ArrayList<>(Arrays.asList(INT, INT)), BOOL);
        temp.put(new ArrayList<>(Arrays.asList(FLOAT, FLOAT)), BOOL);
        temp.put(new ArrayList<>(Arrays.asList(STR, STR)), BOOL);
        temp.put(new ArrayList<>(Arrays.asList(BOOL, BOOL)), BOOL);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.BIT_AND, temp);
        temp.put(new ArrayList<>(Arrays.asList(INT, INT)), INT);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.BIT_OR, temp);
        temp.put(new ArrayList<>(Arrays.asList(INT, INT)), INT);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.LOGICAL_AND, temp);
        temp.put(new ArrayList<>(Arrays.asList(BOOL, BOOL)), BOOL);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.LOGICAL_OR, temp);
        temp.put(new ArrayList<>(Arrays.asList(BOOL, BOOL)), BOOL);
    }

    public static boolean canTypeConvertTo(TypeDesc fromType, TypeDesc toType) {
        if (!fromType.isSimple() || !toType.isSimple()) {
            return false;
        }
        return TYPE_CONVERTIBILITY.containsKey(fromType.getBaseType())
                && TYPE_CONVERTIBILITY.get(fromType.getBaseType()).contains(toType.getBaseType());
    }

    public static ExprNode typeConvert(ExprNode expr, TypeDesc type) throws SemanticException {
        return typeConvert(expr, type, null, null);
    }

    public static ExprNode typeConvert(ExprNode expr, TypeDesc type, AstNode exceptNode, String comment) throws SemanticException {
        if (expr.getNodeType() == null) {
            throw exceptNode.semanticError("Тип выражения не определен");
        }
        if (expr.getNodeType().equals(type)) {
            return expr;
        }
        if (expr.getNodeType().isSimple() && type.isSimple() &&
                TYPE_CONVERTIBILITY.containsKey(expr.getNodeType().getBaseType()) &&
                TYPE_CONVERTIBILITY.get(expr.getNodeType().getBaseType()).contains(type.getBaseType())) {
            return new TypeConvertNode(expr, type);
        } else {
            throw (exceptNode != null ? exceptNode : expr).semanticError(
                    String.format("Тип %s%s не конвертируется в %s",
                            expr.getNodeType().toString(),
                            comment != null && !comment.isBlank() ? String.format(" (%s)", comment) : "",
                            type.toString()
                    )
            );
        }
    }
}
