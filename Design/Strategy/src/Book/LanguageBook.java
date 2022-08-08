package Book;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: 策略模式 Book
 * @author: immDream
 * @since: 2022/05/04/15:51
 */
public class LanguageBook extends Book{

    public LanguageBook(double price) {
        this.type = "语言类";
        this.price = price;
    }
    @Override
    public void discount() {
        this.price -= (int)(this.price / 100) * 10;
    }
}
