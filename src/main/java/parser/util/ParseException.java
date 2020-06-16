package parser.util;

import lexer.Token;

public class ParseException extends Exception {
    private String _msg;

    public ParseException(String msg) {
        this._msg = msg;
    }

    public ParseException(Token token) {
        _msg = String.format("Syntax Error, unexpected token %s", token.getValue());
    }

    /**
     * Returns the detail message string of this throwable.
     *
     * @return the detail message string of this {@code Throwable} instance
     * (which may be {@code null}).
     */
    @Override
    public String getMessage() {
        return _msg;
    }
}
