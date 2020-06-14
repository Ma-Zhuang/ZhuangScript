package lexer;

public enum TokenType {
    /**
     * 关键字
     */
    KEYWORD,
    /**
     * 变量类型
     */
    VARIABLE,
    /**
     * 操作符类型
     */
    OPERATOR,
    BRACKET,
    /**
     * 字符串类型
     */
    STRING,
    /**
     * 浮点类型
     */
    FLOAT,
    /**
     * 整型
     */
    INTEGER,
    /**
     * 布尔类型
     */
    BOOLEAN
}
