package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class AssignStmt extends Stmt {

    public AssignStmt() {
        super(ASTNodeTypes.ASSIGN_STMT,"ASSIGN");
    }

    public static ASTNode parse(PeekTokenIterator iterator) throws ParseException {
        var stmt = new AssignStmt();
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
