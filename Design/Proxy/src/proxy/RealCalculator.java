package proxy;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: Proxy PACKAGE_NAME
 * @author: immDream
 * @since: 2022/04/15/8:40
 */
class RealCalculator implements Calculator {
    @Override
    public double add(double x, double y) {
        return x + y;
    }

    @Override
    public double sub(double x, double y) {
        return x - y;
    }

    @Override
    public double mul(double x, double y) {
        return x * y;
    }

    @Override
    public double div(double x, double y) {
        try{
            if(y == 0) {
                throw new RuntimeException("除零，Infinity");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return x / y;
    }
}
