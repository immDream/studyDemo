import Leader.*;
import Request.PurChaseRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: ChainOfResponsibility PACKAGE_NAME
 * @author: immDream
 * @since: 2022/04/29/8:49
 */
public class Client {
    public static void main(String[] args) {
        Leader director, departmentManager, viceGeneralManager, generalManager, conference;
        director = new Director();
        departmentManager = new DepartmentManager();
        viceGeneralManager = new ViceGeneralManager();
        generalManager = new GeneralManager();
        conference = new Conference();
        // 设置职责链
        director.setSuccessor(departmentManager);
        departmentManager.setSuccessor(viceGeneralManager);
        viceGeneralManager.setSuccessor(generalManager);
        generalManager.setSuccessor(conference);
        System.out.println("处理12万元-----------------------------------------");
        PurChaseRequest request1 = new PurChaseRequest(12);
        director.handleRequest(request1);
        System.out.println("处理55万元-----------------------------------------");
        PurChaseRequest request2 = new PurChaseRequest(55);
        director.handleRequest(request2);
        System.out.println("处理8万元-----------------------------------------");
        PurChaseRequest request3 = new PurChaseRequest(8);
        director.handleRequest(request3);
        System.out.println("处理0.8万元-----------------------------------------");
        PurChaseRequest request4 = new PurChaseRequest(0.8);
        director.handleRequest(request4);

    }
}
