package parser.ast;

import lexer.Token;
import parser.util.ExprHOF;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;
import parser.util.PriorityTable;

public class Expr extends ASTNode {
    private static PriorityTable table = new PriorityTable();

    public Expr() {
        super();
    }

    public Expr(ASTNodeTypes type, Token lexeme) {
        super();
        this.type = type;
        this.lexeme = lexeme;
        this.label = lexeme.getValue();
    }


    /**
     * 左递归：E(k) -> E(k) op(k) E(k+1) | E(k+1)
     * 右递归：
     * E(k) -> E(k+1) E_(k+1)
     * E_(k) -> op(k) E(k+1) E_(k) | null
     * <p>
     * 最高优先级处理：
     * E(t) -> F E_(k) | U E_(k)
     * E_(t) -> op(t) E(t) E_(t) | null
     *
     * @param k
     * @return
     */
    private static ASTNode E(int k, PeekTokenIterator iterator) throws ParseException {
        if (k < table.size() - 1) {
            return combine(iterator, () -> E(k + 1, iterator), () -> E_(k, iterator));
        } else {
            return race(
                    iterator,
                    () -> combine(iterator, () -> U(iterator), () -> E_(k, iterator)),
                    () -> combine(iterator, () -> F(iterator), () -> E_(k, iterator))
            );
        }
    }

    /**
     * 一元表达式
     *
     * @return
     */
    private static ASTNode U(PeekTokenIterator iterator) throws ParseException {
        var token = iterator.peek();
        var value = token.getValue();

        if (value.equals("(")) {
            iterator.nextMatch("(");
            var expr = E(0, iterator);
            iterator.nextMatch(")");
            return expr;
        } else if (value.equals("++") || value.equals("--") || value.equals("!")) {
            var t = iterator.peek();
            iterator.nextMatch(value);
            Expr unaryExpr = new Expr(ASTNodeTypes.UNARY_EXPR, t);
            unaryExpr.addChile(E(0, iterator));
            return unaryExpr;
        }
        return null;
    }

    /**
     * 因子
     *
     * @return
     */
    private static ASTNode F(PeekTokenIterator iterator) throws ParseException{
        var factor = Factor.parse(iterator);
        if (factor==null){
            return null;
        }
        if (iterator.hasNext()&&iterator.peek().getValue().equals("(")){
            return CallExpr.parse(factor,iterator);
        }

        return factor;
    }

    private static ASTNode E_(int k, PeekTokenIterator iterator) throws ParseException {
        var token = iterator.peek();
        var value = token.getValue();

        if (table.get(k).indexOf(value) != -1) {
            var expr = new Expr(ASTNodeTypes.BINARY_EXPR, iterator.nextMatch(value));
            expr.addChile(combine(iterator,
                    () -> E(k + 1, iterator),
                    () -> E_(k, iterator)
            ));
            return expr;
        }
        return null;
    }

    private static ASTNode combine(PeekTokenIterator iterator, ExprHOF aFunc, ExprHOF bFunc) throws ParseException {
        var a = aFunc.hoc();
        if (a == null) {
            return iterator.hasNext() ? bFunc.hoc() : null;
        }
        var b = iterator.hasNext() ? bFunc.hoc() : null;
        if (b == null) {
            return a;
        }

        Expr expr = new Expr(ASTNodeTypes.BINARY_EXPR, b.lexeme);
        expr.addChile(a);
        expr.addChile(b.getChild(0));
        return expr;
    }

    private static ASTNode race(PeekTokenIterator iterator, ExprHOF aFunc, ExprHOF bFunc) throws ParseException {
        if ((!iterator.hasNext())) {
            return null;
        }
        var a = aFunc.hoc();
        if (a != null) {
            return a;
        }
        return bFunc.hoc();
    }

    public static ASTNode parse(PeekTokenIterator tokenIt) throws ParseException {
        return E(0, tokenIt);
    }
}
