package Account;

/**
 * 正常
 *
 * @see: 状态模式 Account
 * @author: 庄宇
 * @since: 2022/05/04/14:25
 */
public class GreenState extends AccountState {
    public GreenState(AccountState state) {
        this.account = state.account;
        this.balance = state.balance;
    }

    public GreenState(double balance, Account acc) {
        this.account = acc;
        this.balance = balance;
    }
    @Override
    public void stateCheck() {
        if(this.balance < -1000) {
            this.account.setState(new RedState(this));
        } else if (this.balance < 0) {
            this.account.setState(new YellowState(this));
        }
    }
}
