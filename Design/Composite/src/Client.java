public class Client {
    public static void main(String[] args) {
        Folder folder = new Folder("杀毒文件夹");
        Folder folder1 = new Folder("杀毒文件夹1");
        File textFile1 = new TextFile("txt1");
        File textFile2 = new TextFile("txt2");
        File textFile3 = new TextFile("txt3");
        File imageFile1 = new ImageFile("image1");
        File imageFile2 = new ImageFile("image2");
        File videoFile = new VideoFile("video");
        // 将文件夹1放到文件夹里
        folder.add(folder1);
        folder.add(textFile1);
        folder.add(textFile2);
        folder.add(imageFile1);
        folder.add(videoFile);
        // 将txt3 image2放到folder1中
        folder1.add(textFile3);
        folder1.add(imageFile2);

        // 查杀文件夹
        folder.killOperation();

        // 查杀指定文件
        System.out.println("\n查杀指定文件...");
        folder.getChild(2).killOperation();
        textFile3.killOperation();
    }
}
