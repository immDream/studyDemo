import java.awt.*;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: 解释器模式 PACKAGE_NAME
 * @author: immDream
 * @since: 2022/04/15/14:53
 */
public class Client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入运算式,用空格分开:");
        String statement = sc.nextLine();
        Calculator calculator = new Calculator();
        calculator.build(statement);
        double result = calculator.calculate();
        System.out.println("运算结果为:");
        System.out.println(statement + " = " +result);
    }
}
