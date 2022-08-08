/**
 * Created with IntelliJ IDEA.
 *
 * @see: 解释器模式 PACKAGE_NAME
 * @author: immDream
 * @since: 2022/04/15/14:34
 */
public class SubNode extends SymbolNode {
    public SubNode(Node left, Node right) {
        super(left, right);
    }

    @Override
    public double interpret() {
        return super.left.interpret() - super.right.interpret();
    }
}
