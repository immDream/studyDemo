package Level;

import Role.Role;

/**
 * 等级
 *
 * @see: 状态模式 Level
 * @author: 庄宇
 * @since: 2022/04/29/16:43
 */
public abstract class Level {
    Role role;
    int score;
    String levelName;

    public void play(boolean result) {
        if(result) {
            score += 1;
            System.out.println(role.getName() + "胜利");
        } else {
            if(score > 0) {
                score -= 1;
            }
            System.out.println(role.getName() + "失败");
        }
        System.out.println(role.getName() + "当前积分为" + score);
        checkLevel(this.score);
    }

    public void checkLevel(int score) {
        if(score >= 10) {
            if(role.getLevel() instanceof GuruLevel) return;
            role.setLevel(new GuruLevel(this));
            System.out.println(role.getName() + "是" + role.getLevel().getLevelName());
        } else if(score >= 5) {
            if(role.getLevel() instanceof SuperiorLevel) return;
            role.setLevel(new SuperiorLevel(this));
            System.out.println(role.getName() + "是" + role.getLevel().getLevelName());
        } else if(score >= 2){
            if(role.getLevel() instanceof PractisedLevel) return;
            role.setLevel(new PractisedLevel(this));
            System.out.println(role.getName() + "是" + role.getLevel().getLevelName());
        } else if(score >= 0) {
            if(role.getLevel() instanceof EntryLevel) return;
            role.setLevel(new EntryLevel(this));
            System.out.println(role.getName() + "是" + role.getLevel().getLevelName());
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
