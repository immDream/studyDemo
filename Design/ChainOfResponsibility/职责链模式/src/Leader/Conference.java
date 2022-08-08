package Leader;

import Request.PurChaseRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: ChainOfResponsibility PACKAGE_NAME
 * @author: immDream
 * @since: 2022/04/29/8:43
 */
public class Conference extends Leader {

    public Conference() {
        super();
    }

    @Override
    public void handleRequest(PurChaseRequest request) {
        System.out.println("通过大型会议审批了价值为" + request.getPrice() + "万元的物资采购单");
    }
}
