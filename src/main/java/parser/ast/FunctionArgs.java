package parser.ast;

import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class FunctionArgs extends ASTNode {
    public FunctionArgs(){
        super();
        this.label = "args";
    }

    public static ASTNode parse(PeekTokenIterator iterator) throws ParseException {

        var args = new FunctionArgs();
        while (iterator.peek().isType()){
            var type = iterator.next();
            var variable = (Variable)Factor.parse(iterator);
            variable.setTypeLexeme(type);
            args.addChile(variable);

            if (!iterator.peek().getValue().equals(")")){
                iterator.nextMatch(",");
            }
        }
        return args;
    }
}
