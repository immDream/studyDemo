package factory;

import mapper.Log;
import mapper.LogFactory;
import util.ConsoleLog;

/**
 * 控制台日志工厂
 * @see: 工厂模式 factory
 * @author: immDream
 * @since: 2022/03/18/8:59
 */
public class ConsoleLogFactory implements LogFactory {

    @Override
    public Log createLog() {
        System.out.println("在工厂中创建控制台对象");
        return new ConsoleLog();
    }
}
