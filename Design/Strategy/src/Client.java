import Book.*;

import java.text.DecimalFormat;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: 策略模式 PACKAGE_NAME
 * @author: immDream
 * @since: 2022/05/04/16:13
 */
public class Client {
    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("#.##");
        Book[] books = new Book[6];
        books[0] = new ComputerBook(20.6);
        books[1] = new ComputerBook(30.6);
        books[2] = new FictionBook(20.6);
        books[3] = new LanguageBook(120.6);
        books[4] = new LanguageBook(48.6);
        books[5] = new FictionBook(55.6);
        DiscountHandler discountHandler = new DiscountHandler();
        for(Book b : books) {
            discountHandler.setBook(b);
            discountHandler.discount();
        }

        for(Book b : books) {
            System.out.println(b.getType() + "的书降价后为" + df.format(b.getPrice()));
        }
    }
}
