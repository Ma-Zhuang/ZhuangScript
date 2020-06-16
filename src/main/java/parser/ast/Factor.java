package parser.ast;

import lexer.TokenType;
import parser.util.PeekTokenIterator;

public abstract class Factor extends ASTNode {
    public Factor(ASTNode _parent, PeekTokenIterator iterator) {
        super(_parent);
        var token = iterator.next();
        var type = token.getType();

        if (type == TokenType.VARIABLE) {
            this.type = ASTNodeTypes.VARIABLE;
        } else {
            this.type = ASTNodeTypes.SCALAR;
        }
        this.label = token.getValue();
        this.lexeme = token;
    }
}
