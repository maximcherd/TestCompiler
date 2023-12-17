package exceptions;

/**
 * Класс для исключений во время семантического анализа
 */
public class SemanticException extends Exception {
    public SemanticException(String message) {
        super(message);
    }

    public SemanticException(String message, Integer row, Integer col) {
        super(getMessage(message, row, col));
    }

    private static String getMessage(String message, Integer row, Integer col) {
        if (row != null || col != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(" (");
            if (row != null) {
                sb.append(String.format("строка: %d", row));
                if (col != null) {
                    sb.append(", ");
                }
            }
            if (col != null) {
                sb.append(String.format("позиция: %d", col));
            }
            message += sb.toString();
        }
        return message;
    }
}
