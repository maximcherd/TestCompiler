package codeGenBase;

/**
 * Класс для описания меток
 */
public class CodeLabel {
    private Integer index;
    private String prefix = "L";

    public CodeLabel() {
    }

    public CodeLabel(String prefix) {
        this.index = null;
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return String.format("%s_%d", this.prefix, this.index);
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
