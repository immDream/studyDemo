package Leader;

import Request.PurChaseRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: ChainOfResponsibility PACKAGE_NAME
 * @author: immDream
 * @since: 2022/04/29/8:43
 */
public class Director extends Leader {

    public Director() {
        super();
    }

    @Override
    public void handleRequest(PurChaseRequest request) {
        if(request.getPrice() < 1) {
            System.out.println("主任审批了价值为" + request.getPrice() + "万元的物资采购单");
        } else {
            if(this.successor != null) {
                System.out.println("主任交给上级处理");
                this.successor.handleRequest(request);
            }
        }
    }
}
