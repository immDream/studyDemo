package Subject;

import Observer.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 订阅类
 *
 * @see: 观察者模式 Subject
 * @author: 庄宇
 * @since: 2022/04/29/9:21
 */
public interface Subject {
    public List<Observer> observerList = new ArrayList<Observer>();
    // 注册
    public void attach(Observer observer);
    // 销毁
    public void detach(Observer observer);
    // 通知
    public void notifyObserver();
}
