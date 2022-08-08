package factory;

import mapper.Log;
import mapper.LogFactory;
import util.DatabaseLog;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: 工厂模式 factory
 * @author: immDream
 * @since: 2022/03/18/8:58
 */
public class DatabaseLogFactory implements LogFactory {
    @Override
    public Log createLog() {
        System.out.println("在工厂中创建数据库日志对象");
        return new DatabaseLog();
    }
}
