package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class Program extends Block {

    public Program() {
        super();
    }
    public static ASTNode parse(PeekTokenIterator iterator) throws ParseException {
        var program = new Program();
        ASTNode stmt = null;
        while ((stmt = Stmt.parseStmt(iterator))!=null){
            program.addChile(stmt);
        }
        return program;
    }
}
