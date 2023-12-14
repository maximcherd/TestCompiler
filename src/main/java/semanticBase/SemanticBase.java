package semanticBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SemanticBase {
    public static BaseType VOID = BaseType.VOID;
    public static BaseType INT = BaseType.INT;
    public static BaseType FLOAT = BaseType.FLOAT;
    public static BaseType BOOL = BaseType.BOOL;
    public static BaseType STR = BaseType.STR;


    public static HashMap<BaseType, ArrayList<BaseType>> TYPE_CONVERTIBILITY;

    static {
        TYPE_CONVERTIBILITY = new HashMap<>();
        TYPE_CONVERTIBILITY.put(INT, (ArrayList<BaseType>) Arrays.asList(FLOAT, BOOL, STR));
        TYPE_CONVERTIBILITY.put(FLOAT, (ArrayList<BaseType>) Arrays.asList(STR));
        TYPE_CONVERTIBILITY.put(BOOL, (ArrayList<BaseType>) Arrays.asList(STR));
    }

    public static HashMap<BinOp, HashMap<ArrayList<BaseType>, BaseType>> BIN_OP_TYPE_COMPATIBILITY;

    static {
        BIN_OP_TYPE_COMPATIBILITY = new HashMap<>();
        HashMap<ArrayList<BaseType>, BaseType> temp;

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.ADD, temp);
        temp.put((ArrayList<BaseType>) Arrays.asList(INT, INT), INT);
        temp.put((ArrayList<BaseType>) Arrays.asList(FLOAT, FLOAT), FLOAT);
        temp.put((ArrayList<BaseType>) Arrays.asList(STR, STR), STR);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.SUB, temp);
        temp.put((ArrayList<BaseType>) Arrays.asList(INT, INT), INT);
        temp.put((ArrayList<BaseType>) Arrays.asList(FLOAT, FLOAT), FLOAT);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.MUL, temp);
        temp.put((ArrayList<BaseType>) Arrays.asList(INT, INT), INT);
        temp.put((ArrayList<BaseType>) Arrays.asList(FLOAT, FLOAT), FLOAT);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.DIV, temp);
        temp.put((ArrayList<BaseType>) Arrays.asList(INT, INT), INT);
        temp.put((ArrayList<BaseType>) Arrays.asList(FLOAT, FLOAT), FLOAT);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.MOD, temp);
        temp.put((ArrayList<BaseType>) Arrays.asList(INT, INT), INT);
        temp.put((ArrayList<BaseType>) Arrays.asList(FLOAT, FLOAT), FLOAT);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.GT, temp);
        temp.put((ArrayList<BaseType>) Arrays.asList(INT, INT), BOOL);
        temp.put((ArrayList<BaseType>) Arrays.asList(FLOAT, FLOAT), BOOL);
        temp.put((ArrayList<BaseType>) Arrays.asList(STR, STR), BOOL);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.LT, temp);
        temp.put((ArrayList<BaseType>) Arrays.asList(INT, INT), BOOL);
        temp.put((ArrayList<BaseType>) Arrays.asList(FLOAT, FLOAT), BOOL);
        temp.put((ArrayList<BaseType>) Arrays.asList(STR, STR), BOOL);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.GE, temp);
        temp.put((ArrayList<BaseType>) Arrays.asList(INT, INT), BOOL);
        temp.put((ArrayList<BaseType>) Arrays.asList(FLOAT, FLOAT), BOOL);
        temp.put((ArrayList<BaseType>) Arrays.asList(STR, STR), BOOL);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.LE, temp);
        temp.put((ArrayList<BaseType>) Arrays.asList(INT, INT), BOOL);
        temp.put((ArrayList<BaseType>) Arrays.asList(FLOAT, FLOAT), BOOL);
        temp.put((ArrayList<BaseType>) Arrays.asList(STR, STR), BOOL);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.EQUALS, temp);
        temp.put((ArrayList<BaseType>) Arrays.asList(INT, INT), BOOL);
        temp.put((ArrayList<BaseType>) Arrays.asList(FLOAT, FLOAT), BOOL);
        temp.put((ArrayList<BaseType>) Arrays.asList(STR, STR), BOOL);
        temp.put((ArrayList<BaseType>) Arrays.asList(BOOL, BOOL), BOOL);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.NEQUALS, temp);
        temp.put((ArrayList<BaseType>) Arrays.asList(INT, INT), BOOL);
        temp.put((ArrayList<BaseType>) Arrays.asList(FLOAT, FLOAT), BOOL);
        temp.put((ArrayList<BaseType>) Arrays.asList(STR, STR), BOOL);
        temp.put((ArrayList<BaseType>) Arrays.asList(BOOL, BOOL), BOOL);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.BIT_AND, temp);
        temp.put((ArrayList<BaseType>) Arrays.asList(INT, INT), INT);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.BIT_OR, temp);
        temp.put((ArrayList<BaseType>) Arrays.asList(INT, INT), INT);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.LOGICAL_AND, temp);
        temp.put((ArrayList<BaseType>) Arrays.asList(BOOL, BOOL), BOOL);

        temp = new HashMap<>();
        BIN_OP_TYPE_COMPATIBILITY.put(BinOp.LOGICAL_OR, temp);
        temp.put((ArrayList<BaseType>) Arrays.asList(BOOL, BOOL), BOOL);
    }

    public boolean canTypeConvertTo(TypeDesc fromType, TypeDesc toType) {
        if (!fromType.isSimple() || !toType.isSimple()) {
            return false;
        }
        return TYPE_CONVERTIBILITY.containsKey(fromType.baseType)
                && TYPE_CONVERTIBILITY.get(fromType.baseType).contains(toType.baseType);
    }
}
