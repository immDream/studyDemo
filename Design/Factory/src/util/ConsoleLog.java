package util;

import mapper.Log;

/**
 * 控制台日志
 * @see: 工厂模式 util
 * @author: 庄宇
 * @since: 2022/03/18/9:01
 */
public class ConsoleLog implements Log {
    @Override
    public void writeLog() {
        System.out.println("写控制台日志");
    }
}
