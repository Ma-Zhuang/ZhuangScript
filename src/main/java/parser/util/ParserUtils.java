package parser.util;

import jdk.jshell.spi.ExecutionControl;
import org.apache.commons.lang3.NotImplementedException;
import parser.ast.ASTNode;

public class ParserUtils {
    /**
     * 将表达式从前序换位后序
     * @param node
     * @return
     */
    public static String toPostFixExpression(ASTNode node){
        //node: left op right -> left right op
        String leftStr = "";
        String rightStr = "";

        switch (node.getType()){
            case BINARY_EXPR:
                leftStr = toPostFixExpression(node.getChild(0));
                rightStr = toPostFixExpression(node.getChild(1));
                return leftStr + " " + rightStr + " " + node.getLexeme().getValue();
            case VARIABLE:
            case SCALAR:
                return node.getLexeme().getValue();
        }

        throw new NotImplementedException("not impl.");
    }
}
