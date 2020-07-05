package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public abstract class Stmt extends ASTNode {
    public Stmt(ASTNodeTypes _type, String _label) {
        super(_type, _label);
    }

    public static ASTNode parseStmt(PeekTokenIterator iterator) throws ParseException {
        if (!iterator.hasNext()){
            return null;
        }
        var token = iterator.next();
        var lookAhead = iterator.peek();
        iterator.putBack();

        if (token.isVariable()&&lookAhead!=null&&lookAhead.getValue().equals("=")){
            return AssignStmt.parse(iterator);
        }else if (token.getValue().equals("var")){
            return DeclareStmt.parse(iterator);
        }else if (token.getValue().equals("func")){
            return FunctionDeclareStmt.parse(iterator);
        }else if (token.getValue().equals("return")){
            return ReturnStmt.parse(iterator);
        }else if(token.getValue().equals("if")){
            return IfStmt.parse(iterator);
        }else if (token.getValue().equals("{")){
            return Block.parse(iterator);
        }else {
            return Expr.parse(iterator);
        }
    }
}
