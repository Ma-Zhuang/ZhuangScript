package parser.ast;

import lexer.Token;
import lexer.TokenType;
import parser.util.PeekTokenIterator;

public abstract class Factor extends ASTNode {
    public Factor(Token token) {
        super();
        this.lexeme = token;
        this.label = token.getValue();
    }

    public static ASTNode parse(PeekTokenIterator iterator) {
        var token = iterator.peek();
        var type = token.getType();

        if (type==TokenType.VARIABLE){
            iterator.next();
            return new Variable(token);
        }else if (token.isScalar()){
            iterator.next();
            return new Scalar(token);
        }
        return null;
    }
}
