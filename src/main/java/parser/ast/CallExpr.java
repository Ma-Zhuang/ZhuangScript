package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class CallExpr extends Expr {
    public CallExpr(){
        super();
        this.label="call";
        this.type=ASTNodeTypes.CALL_EXPR;
    }

    public static ASTNode parse(ASTNode factor, PeekTokenIterator iterator) throws ParseException{
        var expr = new CallExpr();
        expr.addChile(factor);
        iterator.nextMatch("(");
        ASTNode p = null;
        while ((p=Expr.parse(iterator))!=null){
            expr.addChile(p);
            if (!iterator.peek().getValue().equals(")")){
                iterator.nextMatch(",");
            }
        }
        iterator.nextMatch(")");
        return expr;
    }
}
