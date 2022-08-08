package entity;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: 工厂模式 entity
 * @author: immDream
 * @since: 2022/04/01/20:46
 */
public class Triangle extends Shape {
    @Override
    public void draw() {
        System.out.println("正在绘画三角形");
    }

    @Override
    public void erase() {
        System.out.println("擦除三角形");
    }
}
