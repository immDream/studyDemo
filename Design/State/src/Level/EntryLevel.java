package Level;

import Role.Role;

/**
 * 入门级
 *
 * @see: 状态模式 Level
 * @author: 庄宇
 * @since: 2022/04/29/17:09
 */
public class EntryLevel extends Level {

    public EntryLevel(Role role) {
        this.score = 0;
        this.levelName = "入门级";
        this.role = role;
    }

    public EntryLevel(Level level) {
        this.role = level.role;
        this.score = level.getScore();
        this.levelName = "入门级";
    }
}
