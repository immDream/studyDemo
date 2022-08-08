package Book;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: 策略模式 Book
 * @author: immDream
 * @since: 2022/05/04/15:51
 */
public class FictionBook extends Book{

    public FictionBook(double price) {
        this.type = "小说类";
        this.price = price;
    }
    @Override
    public void discount() {
        if(this.price > 2) {
            this.price -= 2;
        }
    }
}
