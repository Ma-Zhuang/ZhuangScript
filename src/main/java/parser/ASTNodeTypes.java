package parser;

public enum ASTNodeTypes {
    /**
     * 代码块
     */
    BLOCK,
    /**
     *二项表达式
     */
    BINARY_EXPR, //1+1
    /**
     * 一元表达式
     */
    UNARY_EXPR, //++i
    /**
     * 变量
     */
    VARIABLE,
    /**
     * 标量
     */
    SCALAR, //1.0, true
    /**
     * 条件判断语句（if）
     */
    IF_STMT,
    /**
     * 循环语句（while）
     */
    WHILE_STMT,
    /**
     * 循环语句（for）
     */
    FOR_STMT,
    /**
     * 赋值语句
     */
    ASSIGN_STMT,
    /**
     * 定义函数的语句
     */
    FUNCTION_DECLARE_STMT,
    /**
     * 声明语句
     */
    DECLARE_STMT
}
