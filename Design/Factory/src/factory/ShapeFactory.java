package factory;


import entity.Circle;
import entity.Shape;
import entity.Square;
import entity.Triangle;
import exception.UnSupportedShapeException;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: 工厂模式 factory
 * @author: immDream
 * @since: 2022/04/01/20:43
 */
public class ShapeFactory {
    public static Shape getShape(String name) throws UnSupportedShapeException {
        name = name.toLowerCase();
        switch (name) {
            case "circle":
                return new Circle();
            case "square":
                return new Square();
            case "triangle":
                return new Triangle();
            default:
                throw new UnSupportedShapeException();
        }
    }
}
