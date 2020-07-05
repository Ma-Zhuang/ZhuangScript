package parser;

import lexer.Lexer;
import lexer.LexicalException;
import org.junit.jupiter.api.Test;
import parser.ast.*;
import parser.util.ParseException;
import parser.util.ParserUtils;
import parser.util.PeekTokenIterator;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StmtTests {

    @Test
    public void declare() throws LexicalException, ParseException {
        var it = createTokenIt("var i = 100 * 2");
        var stmt = DeclareStmt.parse(it);
        assertEquals(ParserUtils.toPostFixExpression(stmt), "i 100 2 * =");
        //assertEquals(ParserUtils.toPostFixExpression(stmt), "xxxx");
    }

    @Test
    public void assign() throws LexicalException, ParseException {
        var it = createTokenIt("i = 100 * 2");
        var stmt = AssignStmt.parse(it);
        assertEquals(ParserUtils.toPostFixExpression(stmt), "i 100 2 * =");
        //assertEquals(ParserUtils.toPostFixExpression(stmt), "xxxx");
    }
    @Test
    public void ifstmt() throws LexicalException, ParseException {
        var it = createTokenIt("if(a){\n" +
                "a = 1\n" +
                "}"
        );

        var stmt = (IfStmt)IfStmt.parse(it);
        var expr = (Variable)stmt.getChild(0);
        var block = (Block)stmt.getChild(1);
        var assignStmt = (AssignStmt)block.getChild(0);

        assertEquals("a", expr.getLexeme().getValue());
        assertEquals("=", assignStmt.getLexeme().getValue());
    }

    @Test
    public void ifElseStmt() throws LexicalException, ParseException {
        var it = createTokenIt("if(a) {\n" +
                "a = 1\n" +
                "} else {\n" +
                "a = 2\n" +
                "a = a * 3" +
                "}"
        );
        var stmt = (IfStmt)IfStmt.parse(it);
        var expr = (Variable)stmt.getChild(0);
        var block = (Block)stmt.getChild(1);
        var assignStmt = (AssignStmt)block.getChild(0);
        var elseBlock = (Block)stmt.getChild(2);
        var assignStmt2 = (AssignStmt)elseBlock.getChild(0);

        assertEquals("a", expr.getLexeme().getValue());
        assertEquals("=", assignStmt.getLexeme().getValue());
        assertEquals("=", assignStmt2.getLexeme().getValue());
        assertEquals(2, elseBlock.getChildren().size());
    }

    @Test
    public void function() throws FileNotFoundException, UnsupportedEncodingException, LexicalException, ParseException {
        var tokens = Lexer.fromFile("D:\\ZhuangScript\\ZhuangScript\\example\\function.zs");
        var functionStmt = (FunctionDeclareStmt)Stmt.parseStmt(new PeekTokenIterator(tokens.stream()));
        var args = functionStmt.getArgs();
        assertEquals("a", args.getChild(0).getLexeme().getValue());
        assertEquals("b", args.getChild(1).getLexeme().getValue());

        var type = functionStmt.getFunctionType();
        assertEquals("int", type);

        var functionVariable = functionStmt.getFunctionVariable();
        assertEquals("add", functionVariable.getLexeme().getValue());

        var block = functionStmt.getFunctionBlock();
        assertEquals(true, block.getChild(0) instanceof ReturnStmt);
    }

    @Test
    public void function1() throws FileNotFoundException, UnsupportedEncodingException, LexicalException, ParseException {
        var tokens = Lexer.fromFile("./example/recursion.zs");
        var functionStmt = (FunctionDeclareStmt)Stmt.parseStmt(new PeekTokenIterator(tokens.stream()));
        functionStmt.print(0);
        assertEquals("FUNCTION fact args BLOCK", ParserUtils.toBFSString(functionStmt, 4));
        assertEquals("args n", ParserUtils.toBFSString(functionStmt.getArgs(), 2));
        assertEquals("BLOCK IF RETURN", ParserUtils.toBFSString(functionStmt.getFunctionBlock(), 3));
    }

    private PeekTokenIterator createTokenIt(String src) throws LexicalException, ParseException {
        var lexer = new Lexer();
        var tokens = lexer.analyse(src.chars().mapToObj(x ->(char)x));
        var tokenIt = new PeekTokenIterator(tokens.stream());
        return tokenIt;
    }

}
