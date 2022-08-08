import Account.Account;
import Account.AccountState;
import Account.GreenState;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: 状态模式 PACKAGE_NAME
 * @author: immDream
 * @since: 2022/05/04/14:38
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        Account account = new Account("张三", 999);
        account.withdraw(1000);
        account.withdraw(1000);
        account.withdraw(1000);
        account.withdraw(1000);
        System.out.println("存款");
        account.deposit(1000);
        account.deposit(1000);
    }
}
