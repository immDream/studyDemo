/**
 * 图形文件类
 */
public class ImageFile extends File {
    private String name;
    public ImageFile() {
        this.name = "新建图形文件";
    }
    public ImageFile(String name) {
        this.name = name;
    }
    @Override
    public void killOperation() {
        System.out.println("查杀图像文件" + name + ".jpg完成");
    }

    @Override
    public String getName() {
        return name;
    }
}
