package parser.ast;

import lexer.Token;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class ASTNode {

    /**
     * 抽象语法树
     */
    protected ArrayList<ASTNode> children = new ArrayList<>();
    protected ASTNode parent;

    /**
     * 关键信息
     */
    protected Token lexeme;//词法单元
    protected String label;//备注（标签）
    protected ASTNodeTypes type;//类型

    private HashMap<String,Object> _props = new HashMap<>();

    public ASTNode(ASTNodeTypes _type, String _label) {
        this.type = _type;
        this.label = _label;
    }

    public ASTNode() {}

    public ASTNode getChild(int index) {
        if (index >= this.children.size()) {
            return null;
        }
        return this.children.get(index);
    }

    public void addChile(ASTNode node) {
        node.parent = this;
        children.add(node);
    }

    public Token getLexeme() {
        return lexeme;
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public void setLexeme(Token token) {
        this.lexeme = token;
    }

    public void setLabel(String s) {
        this.label = s;
    }

    public ASTNodeTypes getType() {
        return this.type;
    }

    public void setType(ASTNodeTypes type) {
        this.type = type;
    }

    public void print(int indent) {
        if(indent == 0) {
            System.out.println("print:" + this);
        }

        System.out.println(StringUtils.leftPad(" ", indent *2) + label);
        for(var child : children) {
            child.print(indent + 1);
        }
    }

    public String getLabel() {
        return this.label;
    }

}
