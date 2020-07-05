package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class ReturnStmt extends Stmt{
    public ReturnStmt() {
        super(ASTNodeTypes.RETURN_STMT,"RETURN");
    }

    public static ASTNode parse(PeekTokenIterator it) throws ParseException {
        var lexeme = it.nextMatch("return");
        var expr = Expr.parse(it);

        var stmt = new ReturnStmt();
        stmt.setLexeme(lexeme);
        if(expr != null) {
            stmt.addChile(expr);
        }
        return stmt;
    }
}
