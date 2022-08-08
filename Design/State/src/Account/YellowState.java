package Account;

/**
 * 欠费
 *
 * @see: 状态模式 Account
 * @author: 庄宇
 * @since: 2022/05/04/14:25
 */
public class YellowState extends AccountState {
    public YellowState(AccountState state) {
        this.account = state.account;
        this.balance = state.balance;
    }
    @Override
    public void stateCheck() {
        if (this.balance >= 0) {
            this.account.setState(new GreenState(this));
        } else if(this.balance < -1000) {
            this.account.setState(new RedState(this));
        }
    }
}
