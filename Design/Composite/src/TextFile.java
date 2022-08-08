/**
 * 文本文件类
 */
public class TextFile extends File {
    private String name;
    public TextFile() {
        this.name = "新建文本文件";
    }
    public TextFile(String name) {
        this.name = name;
    }

    @Override
    public void killOperation() {
        System.out.println("查杀文本文件" + name + ".txt完成");
    }

    @Override
    public String getName() {
        return name;
    }
}
