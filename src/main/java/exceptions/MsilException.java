package exceptions;

/**
 * Класс для исключений во время генерации MSIL
 * (на всякий случай, пока не используется)
 */
public class MsilException extends Exception {
    public MsilException(String message) {
        super(message);
    }
}
