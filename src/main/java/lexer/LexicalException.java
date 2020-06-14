package lexer;

/**
 * 异常处理
 */
public class LexicalException extends Exception {
    private String msg;

    public LexicalException(String _msg) {
        msg = _msg;
    }

    public LexicalException(char c) {
        msg = String.format("Unexpected character %c", c);
    }

    /**
     * Returns the detail message string of this throwable.
     *
     * @return the detail message string of this {@code Throwable} instance
     * (which may be {@code null}).
     */
    @Override
    public String getMessage() {
        return msg;
    }
}
