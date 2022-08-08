package Book;

import java.text.DecimalFormat;

/**
 * 书籍类
 *
 * @see: 策略模式 Book
 * @author: immDream
 * @since: 2022/05/04/15:50
 */
public abstract class Book {
    String type;
    double price;
    public abstract void discount();

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }
}
