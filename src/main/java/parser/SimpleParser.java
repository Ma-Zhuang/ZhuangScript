package parser;

import parser.ast.ASTNode;
import parser.ast.ASTNodeTypes;
import parser.ast.Expr;
import parser.ast.Scalar;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class SimpleParser {
    // Expr -> digit + Expr | digit
    //digit -> 0|1|2|3|4|5|...|9
    public static ASTNode parse(PeekTokenIterator iterator) throws ParseException {

        var expr = new Expr(null);
        var scalar = new Scalar(expr, iterator);
        //base condition(基条件)
        if (!iterator.hasNext()) {
            return scalar;
        }
        expr.setLexeme(iterator.peek());
        iterator.nextMatch("+");
        expr.setLabel("+");
        expr.addChile(scalar);
        expr.setType(ASTNodeTypes.BINARY_EXPR);
        var rightNode = parse(iterator);
        expr.addChile(rightNode);
        return expr;
    }
}
