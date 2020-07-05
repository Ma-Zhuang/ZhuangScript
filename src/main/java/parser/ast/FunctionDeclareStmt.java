package parser.ast;

import lexer.TokenType;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class FunctionDeclareStmt extends Stmt {
    public FunctionDeclareStmt() {
        super(ASTNodeTypes.FUNCTION_DECLARE_STMT, "FUNCTION");
    }

    public static ASTNode parse(PeekTokenIterator iterator) throws ParseException {
        iterator.nextMatch("func");

        var func = new FunctionDeclareStmt();
        var lexeme = iterator.peek();
        var functionVariable = (Variable)Factor.parse(iterator);
        func.setLexeme(lexeme);
        func.addChile(functionVariable);
        iterator.nextMatch("(");
        var args = FunctionArgs.parse(iterator);
        iterator.nextMatch(")");
        func.addChile(args);
        var keyword = iterator.nextMatch(TokenType.KEYWORD);
        if (!keyword.isType()){
            throw new ParseException(keyword);
        }
        functionVariable.setTypeLexeme(keyword);
        var block = Block.parse(iterator);
        func.addChile(block);
        return func;
    }

    public ASTNode getArgs(){
        return this.getChild(1);
    }

    public Variable getFunctionVariable(){
        return (Variable)this.getChild(0);
    }

    public String getFunctionType(){
        return this.getFunctionVariable().getTypeLexeme().getValue();
    }

    public Block getFunctionBlock(){
        return (Block)this.getChild(2);
    }
}
