package Calculate;

/**
 * 定期账户
 *
 * @see: 模板模式 Calculate
 * @author: immDream
 * @since: 2022/05/04/16:51
 */
public class FixedAccount extends BankTemplateMethod{
    @Override
    public void calculate() {
        System.out.println("定期账户利息计算");
    }
}
