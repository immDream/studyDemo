package Leader;

import Request.PurChaseRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: ChainOfResponsibility PACKAGE_NAME
 * @author: immDream
 * @since: 2022/04/29/8:43
 */
public class GeneralManager extends Leader {

    public GeneralManager() {
        super();
    }

    @Override
    public void handleRequest(PurChaseRequest request) {
        if(request.getPrice() < 20) {
            System.out.println("总经理审批了价值为" + request.getPrice() + "万元的物资采购单");
        } else {
            if(this.successor != null) {
                System.out.println("总经理召开会议处理");
                this.successor.handleRequest(request);
            }
        }
    }
}
