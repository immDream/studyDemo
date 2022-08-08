package Calculate;

/**
 * 银行利息计算
 *
 * @see: 模板模式 Calculate
 * @author: immDream
 * @since: 2022/05/04/16:45
 */
public abstract class BankTemplateMethod {
    public void queryInfo() {
        System.out.println("查询信息");
    }

    public abstract void calculate();

    public void showInfo() {
        System.out.println("显示利息");
    }

    public void process() {
        queryInfo();
        calculate();
        showInfo();
    }
}
