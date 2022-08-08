package Leader;

import Request.PurChaseRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: ChainOfResponsibility PACKAGE_NAME
 * @author: immDream
 * @since: 2022/04/29/8:43
 */
public class DepartmentManager extends Leader {

    public DepartmentManager() {
        super();
    }

    @Override
    public void handleRequest(PurChaseRequest request) {
        if(request.getPrice() < 5) {
            System.out.println("部门经理审批了价值为" + request.getPrice() + "万元的物资采购单");
        } else {
            if(this.successor != null) {
                System.out.println("部门经理交给上级处理");
                this.successor.handleRequest(request);
            }
        }
    }
}
