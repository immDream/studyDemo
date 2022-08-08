package Account;

/**
 * 透支
 *
 * @see: 状态模式 Account
 * @author: 庄宇
 * @since: 2022/05/04/14:25
 */
public class RedState extends AccountState {
    public RedState(AccountState state) {
        this.account = state.account;
        this.balance = state.balance;
    }

    @Override
    public void stateCheck() {
        if(this.balance >= 0) {
            this.account.setState(new GreenState(this));
        } else if (this.balance >= -1000) {
            this.account.setState(new YellowState(this));
        }
    }

    @Override
    public void withdraw(double amount) {
        System.out.println("透支状态，不能取款");
    }
}
