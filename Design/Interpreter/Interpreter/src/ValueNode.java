/**
 * Created with IntelliJ IDEA.
 *
 * @see: 解释器模式 PACKAGE_NAME
 * @author: immDream
 * @since: 2022/04/15/14:32
 */
public class ValueNode implements Node {
    private double value;
    public ValueNode(double value) {
        this.value = value;
    }

    @Override
    public double interpret() {
        return value;
    }
}
