/**
 * Created with IntelliJ IDEA.
 *
 * @see: 解释器模式 PACKAGE_NAME
 * @author: immDream
 * @since: 2022/04/15/14:32
 */
public abstract class SymbolNode implements Node {
    protected Node left;
    protected Node right;

    public SymbolNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }
}
