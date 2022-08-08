import memento.Caretaker;
import memento.UseInfoDTO;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: Memento PACKAGE_NAME
 * @author: immDream
 * @since: 2022/04/22/15:54
 */
public class Client {
    public static void main(String[] args) {
        UseInfoDTO useInfoDTO = new UseInfoDTO();
        Caretaker caretaker = new Caretaker();
        System.out.println("状态1");
        useInfoDTO.setAccount("张三1");
        useInfoDTO.setPassword("123456");
        useInfoDTO.setTelNo("1300512215");
        caretaker.setMemento(useInfoDTO.saveMemento());
        useInfoDTO.show();

        System.out.println("状态2");
        useInfoDTO.setAccount("张三2");
        useInfoDTO.setPassword("123123");
        useInfoDTO.setTelNo("124124124132");
        caretaker.setMemento(useInfoDTO.saveMemento());
        useInfoDTO.show();

        System.out.println("状态3");
        useInfoDTO.setAccount("张三3");
        useInfoDTO.setPassword("1111111");
        useInfoDTO.setTelNo("1232r234214");
        caretaker.setMemento(useInfoDTO.saveMemento());
        useInfoDTO.show();

        System.out.println("状态4");
        useInfoDTO.setAccount("张三4");
        useInfoDTO.setPassword("123165161");
        useInfoDTO.setTelNo("2212165461");
        caretaker.setMemento(useInfoDTO.saveMemento());
        useInfoDTO.show();

        System.out.println("------------撤销---------------");
        useInfoDTO.restoreMemento(caretaker.getMemento());
        useInfoDTO.show();
        System.out.println("------------撤销---------------");
        useInfoDTO.restoreMemento(caretaker.getMemento());
        useInfoDTO.show();
        System.out.println("------------撤销---------------");
        useInfoDTO.restoreMemento(caretaker.getMemento());
        useInfoDTO.show();
        System.out.println("------------撤销---------------");
        useInfoDTO.restoreMemento(caretaker.getMemento());
        useInfoDTO.show();
        System.out.println("------------撤销次数超出实际数量---------------");
        useInfoDTO.restoreMemento(caretaker.getMemento());
        useInfoDTO.show();
    }
}
