package Observer;

/**
 * 股票观察者
 *
 * @see: 观察者模式 Observer
 * @author: 庄宇
 * @since: 2022/04/29/9:20
 */
public class ConcreteStockObserver implements Observer {
    private Stock s;

    public ConcreteStockObserver (Stock s) {
        this.s = s;
    }

    @Override
    public void update() {
        if(s.getVariation() >= 5) {
            System.out.println("该股票相较于上次上涨" + s.getVariation() + "%");
        } else if(s.getVariation() <= -5) {
            System.out.println("该股票相较于上次跌损" + Math.abs(s.getVariation()) + "%");
        }
    }
}


