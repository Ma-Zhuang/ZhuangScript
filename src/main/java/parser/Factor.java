package parser;

public abstract class Factor extends ASTNode {
    public Factor(ASTNode _parent, ASTNodeTypes _type, String _label) {
        super(_parent, _type, _label);
    }
}
