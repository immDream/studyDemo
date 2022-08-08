package Account;

/**
 * 状态类
 *
 * @see: 状态模式 Account
 * @author: 庄宇
 * @since: 2022/05/04/14:18
 */
public abstract class AccountState {
    Account account;
    Double balance;

    public abstract void stateCheck();

    /**
     * 存款
     * @param amount
     */
    public void deposit(double amount) {
        this.balance += amount;
        stateCheck();
        System.out.println(this.account.getOwner() + "的账户余额为" + this.balance);
        System.out.println(this.account.getState());
    }

    /**
     * 取款
     * @param amount
     */
    public void withdraw(double amount) {
        this.balance -= amount;
        stateCheck();
        System.out.println(this.account.getOwner() + "的账户余额为" + this.balance);
        System.out.println(this.account.getState());
    }

}
