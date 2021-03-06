package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class DeclareStmt extends Stmt {
    public DeclareStmt() {
        super(ASTNodeTypes.DECLARE_STMT, "DECLARE");
    }

    public static ASTNode parse(PeekTokenIterator iterator) throws ParseException {
        var stmt = new DeclareStmt();
        iterator.nextMatch("var");
        var tkn = iterator.peek();
        var factor = Factor.parse(iterator);
        if (factor==null){
            throw new ParseException(tkn);
        }
        stmt.addChile(factor);
        var lexeme = iterator.nextMatch("=");
        var expr = Expr.parse(iterator);
        stmt.addChile(expr);
        stmt.setLexeme(lexeme);
        return stmt;
    }
}
