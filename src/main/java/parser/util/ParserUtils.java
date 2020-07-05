package parser.util;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import parser.ast.ASTNode;
import parser.ast.Factor;
import parser.ast.FunctionDeclareStmt;

import java.util.ArrayList;
import java.util.LinkedList;

public class ParserUtils {
    /**
     * 将表达式从前序换位后序
     * @param node
     * @return
     */
    public static String toPostFixExpression(ASTNode node){
        //node: left op right -> left right op
        /*String leftStr = "";
        String rightStr = "";
        System.out.println(node.getType());
        switch (node.getType()){
            case BINARY_EXPR:
            case DECLARE_STMT:
            case ASSIGN_STMT:
                leftStr = toPostFixExpression(node.getChild(0));
                rightStr = toPostFixExpression(node.getChild(1));
                return leftStr + " " + rightStr + " " + node.getLexeme().getValue();
            case VARIABLE:
            case SCALAR:
                return node.getLexeme().getValue();
        }

        throw new NotImplementedException("not impl.");*/

        if (node instanceof Factor){
            return node.getLexeme().getValue();
        }

        var prts = new ArrayList<String>();
        for (var child:node.getChildren()){
            prts.add(toPostFixExpression(child));
        }

        var lexemeStr = node.getLexeme() != null?node.getLexeme().getValue():"";
        if (lexemeStr.length()>0){
            return StringUtils.join(prts," ")+" "+lexemeStr;
        }else {
            return StringUtils.join(prts," ");
        }
    }

    public static String toBFSString(ASTNode root, int max) {
        var queue = new LinkedList<ASTNode>();
        var list = new ArrayList<String>();
        queue.add(root);

        int c = 0;
        while (queue.size()>0&&c++<max){
            var node = queue.poll();
            list.add(node.getLabel());
            for (var child:node.getChildren()){
                queue.add(child);
            }
        }
        return StringUtils.join(list," ");
    }
}
