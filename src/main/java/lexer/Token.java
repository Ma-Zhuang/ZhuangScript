package lexer;

public class Token {
    /**
     * TokenType和String构成一个元组
     */
    TokenType _type;//类型
    String _value;//值

    /**
     * 构造函数
     * @param tokenType
     * @param value
     */
    public Token(TokenType tokenType,String value){
        this._type = tokenType;
        this._value = value;
    }


    /**
     * 获取当前类型
     * @return 类型
     */
    public TokenType getType(){
        return _type;
    }

    /**
     * 打印type和value的值
     * @return
     */
    @Override
    public String toString() {
        return String.format("type:%s, value=%s",_type,_value);
    }

    /**
     * 判断是否为变量
     * @return
     */
    public boolean isVariable(){
        return _type == TokenType.VARIABLE;
    }

    /**
     * 判断值的类型
     * @return
     */
    public boolean isScalar(){
        return _type==TokenType.INTEGER || _type==TokenType.FLOAT
                || _type==TokenType.STRING || _type==TokenType.BOOLEAN;
    }
}
