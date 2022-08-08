package Book;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: 策略模式 Book
 * @author: immDream
 * @since: 2022/05/04/15:51
 */
public class ComputerBook extends Book{

    public ComputerBook(double price) {
        this.type = "计算机类";
        this.price = price;
    }
    @Override
    public void discount() {
        this.price *= 0.9;
    }
}
