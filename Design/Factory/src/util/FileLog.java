package util;

import mapper.Log;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: 工厂模式 util
 * @author: immDream
 * @since: 2022/03/18/8:56
 */
public class FileLog implements Log {
    @Override
    public void writeLog() {
        System.out.println("写文件日志");
    }
}
