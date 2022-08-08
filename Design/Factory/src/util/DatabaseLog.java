package util;

import mapper.Log;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: 工厂模式 util
 * @author: 庄宇
 * @since: 2022/03/18/8:58
 */
public class DatabaseLog implements Log {
    @Override
    public void writeLog() {
        System.out.println("写数据库日志");
    }
}
