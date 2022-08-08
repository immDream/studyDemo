package memento;

import memento.Memento;

import java.util.ArrayList;
import java.util.List;

/**
 * 负责人
 *
 * @see: Memento PACKAGE_NAME
 * @author: immDream
 * @since: 2022/04/22/12:11
 */
public class Caretaker {
    private List<Memento> mementoList = null;

    public Caretaker() {
        mementoList = new ArrayList<>();
    }

    /**
     * 获取备忘录中的数据
     * @return  如果数据不存在则不进行获取
     */
    public Memento getMemento() {
        int size = mementoList.size();
        if(size <= 0) {
            System.err.println("数据不存在, 无法回退!!");
            return null;
        }
        return mementoList.remove(size - 1);
    }

    public void setMemento(Memento memento) {
        mementoList.add(memento);
    }
}
