package codeGenBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CodeGenerator {
    private List<CodeLine> codeLines = new ArrayList<>();
    private String indent = "";

    public CodeGenerator() {
    }

    public void add(String code) {
        add(code, null, null);
    }

    public void add(CodeLabel code) {
        add(code, null, null);
    }

    public void add(Object code, Object param) {
        add(code, new ArrayList<>(Arrays.asList(param)), null);
    }

    public void add(Object code, List<Object> params) {
        add(code, params, null);
    }

    public void add(Object code, List<Object> params, CodeLabel label) {
        if (code instanceof CodeLabel) {
            label = (CodeLabel)code;
            code = null;
        }
        if (code != null && ((String) code).length() > 0 && ((String) code).charAt(((String) code).length() - 1) == '}') {
            this.indent = this.indent.substring(2);
        }
        this.codeLines.add(new CodeLine(code, params, label, this.indent));
        if (code != null && ((String) code).length() > 0 && ((String) code).charAt(((String) code).length() - 1) == '{') {
            this.indent = this.indent + "  ";
        }
    }

    public List<String> code() {
        int index = 0;
        for (CodeLine cl : this.codeLines) {
            if (cl.getLabel() != null) {
                cl.getLabel().setIndex(index);
                index++;
            }
        }
        List<String> code = new ArrayList<>();
        for (CodeLine cl : this.codeLines) {
            code.add(cl.toString());
        }
        return code;
    }

    public List<CodeLine> getCodeLines() {
        return codeLines;
    }

    public void setCodeLines(List<CodeLine> codeLines) {
        this.codeLines = codeLines;
    }

    public String getIndent() {
        return indent;
    }

    public void setIndent(String indent) {
        this.indent = indent;
    }
}
