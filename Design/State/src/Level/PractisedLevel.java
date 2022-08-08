package Level;

/**
 * 熟练级
 *
 * @see: 状态模式 Level
 * @author: 庄宇
 * @since: 2022/04/29/17:16
 */
public class PractisedLevel extends EntryLevel {

    public PractisedLevel(Level level) {
        super(level);
        this.levelName = "熟练级";
    }

    @Override
    public void play(boolean result) {
        if(result) {
            doubleVictoryScore();
            System.out.println(role.getName() + "获得胜利");
        } else {
            super.play(result);
        }
        System.out.println(this.role.getName() + "当前的积分为" + this.score);
        checkLevel(this.score);
    }

    public void doubleVictoryScore() {
        this.score += 2;
    }
}
