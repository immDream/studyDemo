import java.util.ArrayList;
import java.util.List;

/**
 * 文件夹类 - 继承构件
 */
public class Folder extends AntiVirus {
    public String name;
    private List<AntiVirus> children = new ArrayList<>();

    public Folder() {
        name = "新建文件夹";
    }

    public Folder(String name) {
        this.name = name;
    }

    /**
     * 增加文件
     * @param c 控件类
     */
    public void add(AntiVirus c) {
        children.add(c);
    }

    /**
     * 移除文件
     * @param c
     */
    public void remove(AntiVirus c) {
        children.remove(c);
    }

    /**
     *  移除多组文件
     * @param cs
     */
    public void remove(AntiVirus[] cs) {
        for(AntiVirus c : cs) {
            children.remove(c);
        }
    }

    /**
     * 获取相应控件
     * @param i
     * @return
     */
    public AntiVirus getChild(int i) {
        return children.get(i);
    }

    @Override
    public void killOperation() {
        System.out.println("查杀文件夹" + name + "下所有文件:");
        for(AntiVirus c : children) {
            System.out.print(name + "中的" + c.getName() + "开始杀毒...\t");
            c.killOperation();
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
