package Subject;

import Observer.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 订阅者
 *
 * @see: 观察者模式 Subject
 * @author: 庄宇
 * @since: 2022/04/29/9:29
 */
public class ConcreteSubject implements Subject{
    @Override
    public void attach(Observer observer) {
        // 没有订阅则订阅
        if(!observerList.contains(observer))
            observerList.add(observer);
    }

    @Override
    public void notifyObserver() {
        for(Observer o : observerList) {
            o.update();
        }
    }

    @Override
    public void detach(Observer observer) {
        observerList.remove(observer);
    }
}
