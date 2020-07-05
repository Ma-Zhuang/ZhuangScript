package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class IfStmt extends Stmt {
    public IfStmt() {
        super(ASTNodeTypes.IF_STMT, "IF");
    }

    public static ASTNode parse(PeekTokenIterator iterator) throws ParseException {
        return parseIF(iterator);
    }

    /**
     * IfStmt -> If(Expr) Block Tail
     * @param iterator
     * @return
     * @throws ParseException
     */
    public static ASTNode parseIF(PeekTokenIterator iterator) throws ParseException {
        var lexeme = iterator.nextMatch("if");
        iterator.nextMatch("(");
        var ifStmt = new IfStmt();
        ifStmt.setLexeme(lexeme);
        var expr = Expr.parse(iterator);
        ifStmt.addChile(expr);
        iterator.nextMatch(")");
        var block = Block.parse(iterator);
        ifStmt.addChile(block);

        var tail = parseTail(iterator);
        if (tail!=null){
            ifStmt.addChile(tail);
        }
        return ifStmt;
    }

    /**
     * Tail -> else {Block} | else IfStmt | null
     * @param parent
     * @param iterator
     * @return
     */
    public static ASTNode parseTail(PeekTokenIterator iterator) throws ParseException {
        if (!iterator.hasNext()|| !iterator.peek().getValue().equals("else")){
            return null;
        }
        iterator.nextMatch("else");
        var lookAhead = iterator.peek();
        if (lookAhead.getValue().equals("{")){
            return Block.parse(iterator);
        }else if (lookAhead.getValue().equals("if")){
            return parseIF(iterator);
        }else {
            return null;
        }
    }

    public ASTNode getExpr(){
        return this.getChild(0);
    }

    public ASTNode getBlock(){
        return this.getChild(1);
    }

    public ASTNode getElseBlock(){
        var block = this.getChild(2);
        if (block instanceof Block){
            return block;
        }
        return null;
    }

    public ASTNode getElseIfStmt(){
        var ifStmt = this.getChild(2);
        if (ifStmt instanceof IfStmt){
            return ifStmt;
        }
        return null;
    }
}
