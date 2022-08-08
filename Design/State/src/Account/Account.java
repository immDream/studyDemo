package Account;

/**
 * 账户类
 *
 * @see: 状态模式 Account
 * @author: 庄宇
 * @since: 2022/05/04/14:11
 */
public class Account {
    private AccountState state;
    private String owner;

    public Account(String owner, double init) {
        this.owner = owner;
        setState(new GreenState(init, this));
    }

    public void setState(AccountState state) {
        this.state = state;
    }

    /**
     * 存款
     * @param amount
     */
    public void deposit(double amount) {
        this.state.deposit(amount);
    }

    /**
     * 取款
     * @param amount
     */
    public void withdraw(double amount) {
        this.state.withdraw(amount);
    }

    public String getOwner() {
        return owner;
    }

    public AccountState getState() {
        return state;
    }
}
