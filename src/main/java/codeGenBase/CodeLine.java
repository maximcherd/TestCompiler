package codeGenBase;

import java.util.ArrayList;
import java.util.List;

public class CodeLine {
    private Object code;
    private List<Object> params = new ArrayList<>();
    private CodeLabel label = null;
    private String indent = null;

    /**
     * @param code String or CodeLabel
     * @param params List of String or CodeLabel
     */
    public CodeLine(Object code, List<Object> params, CodeLabel label, String indent) {
        if (code instanceof CodeLabel) {
            label = (CodeLabel) code;
            code = null;
        }
        this.code = code;
        this.params = params;
        this.label = label;
        this.indent = indent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.label != null) {
            if (this.indent != null) {
                sb.append(this.indent.substring(2));
            }
            sb.append(this.label).append(":");
            if (this.code != null) {
                sb.append(" ");
            }
        } else {
            if (this.indent != null) {
                sb.append(this.indent);
            }
        }
        if (this.code != null) {
            sb.append(this.code.toString());
            if (this.params != null) {
                for (Object p : this.params) {
                    sb.append(" ").append(p.toString());
                }
            }
        }
        return sb.toString();
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    public CodeLabel getLabel() {
        return label;
    }

    public void setLabel(CodeLabel label) {
        this.label = label;
    }

    public String getIndent() {
        return indent;
    }

    public void setIndent(String indent) {
        this.indent = indent;
    }
}
