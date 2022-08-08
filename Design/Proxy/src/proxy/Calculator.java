package proxy;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: Proxy PACKAGE_NAME
 * @author: immDream
 * @since: 2022/04/15/8:40
 */
public interface Calculator {
    /**
     *  x + y
     * @param x
     * @param y
     * @return
     */
    double add(double x, double y);

    /**
     *  x - y
     * @param x
     * @param y
     * @return
     */
    double sub(double x, double y);

    /**
     *  x * y
     * @param x
     * @param y
     * @return
     */
    double mul(double x, double y);

    /**
     *  x / y
     * @param x
     * @param y
     * @return
     */
    double div(double x, double y);
}
