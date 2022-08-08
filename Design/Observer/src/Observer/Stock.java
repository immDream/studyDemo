package Observer;

/**
 * 股票
 *
 * @see: 观察者模式 Observer
 * @author: 庄宇
 * @since: 2022/04/29/11:35
 */
public class Stock {
    private double price;
    private double variation;

    public Stock() {}
    public Stock(double price) { this.price = price; }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        // 变化幅度
        this.variation = (price - this.price) / this.price * 100;
        this.price = price;
    }

    public double getVariation() {
        return variation;
    }
}