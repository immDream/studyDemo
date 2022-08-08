package Level;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: 状态模式 Level
 * @author: 状态
 * @since: 2022/04/29/17:41
 */
public class GuruLevel extends SuperiorLevel {
    public GuruLevel(Level level) {
        super(level);
        this.levelName = "骨灰级";
    }

    @Override
    public void play(boolean result) {
        lookOther();
        super.play(result);
    }

    public void lookOther() {
        System.out.println(role.getName() + "使用了查看卡牌技能");
    }
}
