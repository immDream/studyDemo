/**
 * 抽象文件
 */
public abstract class File extends AntiVirus {
    @Override
    public void killOperation() {
        System.out.println("查杀所有类型的文件");
    }
}
