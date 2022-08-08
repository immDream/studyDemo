package Role;

import Level.*;

/**
 * 角色类
 *
 * @see: 状态模式 Role
 * @author: 庄宇
 * @since: 2022/04/29/16:43
 */
public class Role {
    private String name;
    private Level level;

    public Role() {
        level = new EntryLevel(this);
    }

    public void play() {
        level.play(true);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
