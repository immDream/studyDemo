import Calculate.BankTemplateMethod;
import Calculate.CurrentAccount;
import Calculate.FixedAccount;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: 模板模式 PACKAGE_NAME
 * @author: immDream
 * @since: 2022/05/04/16:54
 */
public class Client {
    public static void main(String[] args) {
        BankTemplateMethod bankTemplateMethod;
        bankTemplateMethod = new CurrentAccount();
        bankTemplateMethod.process();
        System.out.println();
        System.out.println();
        bankTemplateMethod = new FixedAccount();
        bankTemplateMethod.process();
    }
}
