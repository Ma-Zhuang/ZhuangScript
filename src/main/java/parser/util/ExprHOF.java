package parser.util;

import parser.ast.ASTNode;

/**
 * HOF: high order function
 */
@FunctionalInterface
public interface ExprHOF {
    ASTNode hoc() throws ParseException;
}
