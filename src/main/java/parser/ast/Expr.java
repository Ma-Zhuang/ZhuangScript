package parser.ast;

import lexer.Token;
import parser.util.ExprHOF;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;
import parser.util.PriorityTable;

public class Expr extends ASTNode {
    private static PriorityTable table = new PriorityTable();

    public Expr(ASTNode parent) {
        super(parent);
    }

    public Expr(ASTNode parent, ASTNodeTypes type, Token lexeme) {
        super(parent);
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
    private static ASTNode E(ASTNode parent, int k, PeekTokenIterator iterator) throws ParseException {
        if (k < table.size() - 1) {
            return combine(parent, iterator, () -> E(parent, k + 1, iterator), () -> E_(parent, k, iterator));
        } else {
            return race(
                    iterator,
                    () -> combine(parent, iterator, () -> U(parent,iterator), () -> E_(parent, k, iterator)),
                    () -> combine(parent, iterator, () -> F(parent,iterator), () -> E_(parent, k, iterator))
            );
        }
    }

    /**
     * 一元表达式
     * @return
     */
    private static ASTNode U(ASTNode parent,PeekTokenIterator iterator) throws ParseException {
        var token = iterator.peek();
        var value = token.getValue();

        if (value.equals("(")){
            iterator.nextMatch("(");
            var expr = E(parent,0,iterator);
            iterator.nextMatch(")");
            return expr;
        }else if (value.equals("++")||value.equals("--")||value.equals("!")){
            var t = iterator.peek();
            iterator.nextMatch(value);
            Expr unaryExpr = new Expr(parent,ASTNodeTypes.UNARY_EXPR,t);
            unaryExpr.addChile(E(unaryExpr,0,iterator));
            return unaryExpr;
        }
        return null;
    }

    /**
     *因子
     * @return
     */
    private static ASTNode F(ASTNode parent,PeekTokenIterator iterator) {
        var token = iterator.peek();
        if (token.isVariable()){
            return new Variable(parent,iterator);
        }else {
            return new Scalar(parent,iterator);
        }
    }

    private static ASTNode E_(ASTNode parent, int k, PeekTokenIterator iterator) throws ParseException {
        var token = iterator.peek();
        var value = token.getValue();

        if (table.get(k).indexOf(value)!=-1){
            var expr = new Expr(parent,ASTNodeTypes.BINARY_EXPR,iterator.nextMatch(value));
            expr.addChile(combine(parent,iterator,
                    () -> E(parent,k+1,iterator),
                    () -> E_(parent,k,iterator)
            ));
            return expr;
        }
        return null;
    }

    private static ASTNode combine(ASTNode parent, PeekTokenIterator iterator, ExprHOF aFunc, ExprHOF bFunc) throws ParseException {
        var a = aFunc.hoc();
        if (a == null) {
            return iterator.hasNext() ? bFunc.hoc() : null;
        }
        var b = iterator.hasNext() ? bFunc.hoc() : null;
        if (a == null) {
            return a;
        }

        Expr expr = new Expr(parent,ASTNodeTypes.BINARY_EXPR,b.lexeme);
//        expr.type = ASTNodeTypes.BINARY_EXPR;
//        expr.lexeme = b.lexeme;
//        expr.label = b.label;
        expr.addChile(a);
        expr.addChile(b.getChild(0));
        return expr;
    }

    private static ASTNode race(PeekTokenIterator iterator,ExprHOF aFunc, ExprHOF bFunc) throws ParseException {
        if ((!iterator.hasNext())){
            return null;
        }
        var a = aFunc.hoc();
        if (a!=null){
            return a;
        }
        return bFunc.hoc();
    }
}
