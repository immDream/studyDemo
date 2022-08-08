package Level;

/**
 * 高手级
 *
 * @see: 状态模式 Level
 * @author: 庄宇
 * @since: 2022/04/29/17:38
 */
public class SuperiorLevel extends PractisedLevel {
    public SuperiorLevel(Level level) {
        super(level);
        this.levelName = "高手级";
    }

    @Override
    public void play(boolean result) {
        exchange();
        super.play(result);
    }

    public void exchange() {
        System.out.println(role.getName() + "使用了交换卡牌技能");
    }
}
