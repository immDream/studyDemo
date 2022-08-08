package Calculate;

/**
 * 活期账户
 *
 * @see: 模板模式 Calculate
 * @author: immDream
 * @since: 2022/05/04/16:50
 */
public class CurrentAccount extends BankTemplateMethod{
    @Override
    public void calculate() {
        System.out.println("活期账户利息计算");
    }
}
