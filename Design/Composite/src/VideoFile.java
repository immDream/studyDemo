/**
 * 视频文件类
 */
public class VideoFile extends File {
    private String name;
    public VideoFile() {
        this.name = "新建视频文件";
    }
    public VideoFile(String name) {
        this.name = name;
    }

    @Override
    public void killOperation() {
        System.out.println("查杀视频文件" + name + ".mp4完成");
    }

    @Override
    public String getName() {
        return name;
    }
}
