import entity.Circle;
import entity.Shape;
import entity.Square;
import entity.Triangle;
import exception.UnSupportedShapeException;
import factory.ConsoleLogFactory;
import factory.FileLogFactory;
import factory.ShapeFactory;
import mapper.Log;
import mapper.LogFactory;
import util.XMLUtil;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: 工厂模式 PACKAGE_NAME
 * @author: immDream
 * @since: 2022/03/18/9:02
 */
public class Client {
    public static void main(String[] args) throws UnSupportedShapeException {
//        LogFactory logFactory = (LogFactory) XMLUtil.getBean();
//        Log log = logFactory.createLog();
//        log.writeLog();
        Shape circle = ShapeFactory.getShape("circle");
        Shape triangle = ShapeFactory.getShape("triangle");
        Shape square = ShapeFactory.getShape("square");
        circle.draw();
        triangle.draw();
        square.draw();
        circle.erase();
        triangle.erase();
        square.erase();
        Shape s = ShapeFactory.getShape("s");
    }
}
