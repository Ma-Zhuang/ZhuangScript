package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class Block extends Stmt {
    public Block() {
        super(ASTNodeTypes.BLOCK, "BLOCK");
    }

    public static ASTNode parse(PeekTokenIterator iterator) throws ParseException {
        iterator.nextMatch("{");
        var block = new Block();
        ASTNode stmt = null;
        while ((stmt = Stmt.parseStmt(iterator))!=null){
            block.addChile(stmt);
        }
        iterator.nextMatch("}");
        return block;
    }
}
