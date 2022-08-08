package Book;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: 策略模式 Book
 * @author: immDream
 * @since: 2022/05/04/16:11
 */
public class DiscountHandler {
    Book book;
    public void discount() {
        this.book.discount();
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
