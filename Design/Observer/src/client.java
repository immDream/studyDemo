import Observer.*;
import Subject.*;

/**
 * 测试
 *
 * @see: 观察者模式 PACKAGE_NAME
 * @author: 庄宇
 * @since: 2022/04/29/11:32
 */
public class client {
    public static void main(String[] args) {
        Stock stock = new Stock(12);
        Subject subject = new ConcreteSubject();
        Observer observer = new ConcreteStockObserver(stock);
        System.out.println("第一次股票波动");
        subject.attach(observer);
        stock.setPrice(8);
        subject.notifyObserver();
        System.out.println("第二次股票波动");
        stock.setPrice(11);
        subject.notifyObserver();
    }
}
