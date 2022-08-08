package factory;

import mapper.Log;
import mapper.LogFactory;
import util.FileLog;

/**
 * 文件日志工厂
 * @see: 工厂模式 factory
 * @author: immDream
 * @since: 2022/03/18/8:56
 */
public class FileLogFactory implements LogFactory {

    @Override
    public Log createLog() {
        System.out.println("在工厂中创建一个文件日志对象");
        return new FileLog();
    }
}
