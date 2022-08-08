package exception;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: 工厂模式 exception
 * @author: immDream
 * @since: 2022/04/01/20:50
 */
public class UnSupportedShapeException extends Exception {
    public UnSupportedShapeException() {
        super("不支持绘制此图形");
    }
}
