package proxy;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: Proxy PACKAGE_NAME
 * @author: immDream
 * @since: 2022/04/15/8:40
 */
public class ProxyCalculator implements Calculator {
    private Calculator realCalculator;

    public ProxyCalculator () {
        realCalculator = new RealCalculator();
    }

    public ProxyCalculator (RealCalculator realCalculator) {
        this.realCalculator = realCalculator;
    }

    @Override
    public double add(double x, double y) {
        return realCalculator.add(x, y);
    }

    @Override
    public double sub(double x, double y) {
        return realCalculator.sub(x, y);
    }

    @Override
    public double mul(double x, double y) {
        return realCalculator.mul(x, y);
    }

    @Override
    public double div(double x, double y) {
        return realCalculator.div(x, y);
    }
}
