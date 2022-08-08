import proxy.Calculator;
import proxy.ProxyCalculator;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: Proxy PACKAGE_NAME
 * @author: immDream
 * @since: 2022/04/15/8:46
 */
public class Client {
    public static void main(String[] args) {
        Calculator calculator = new ProxyCalculator();
        System.out.println("1 + 2 = " + calculator.add(1, 2));
        System.out.println("3 * 2 = " + calculator.mul(3, 2));
        System.out.println("1 - 2 = " + calculator.sub(1, 2));
        /**
         * 抛出一个自定义的除零异常
         */
        System.out.println("1 / 0 = " + calculator.div(1, 0));
        System.out.println("1 / 2 = " + calculator.div(1, 2));
    }
}
