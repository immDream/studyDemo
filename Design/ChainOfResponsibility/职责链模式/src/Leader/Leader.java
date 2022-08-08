package Leader;

import Request.PurChaseRequest;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: ChainOfResponsibility PACKAGE_NAME
 * @author: immDream
 * @since: 2022/04/29/8:41
 */
public abstract class Leader {
    protected Leader successor;

    public Leader() {}

    public void setSuccessor(Leader successor) {
        this.successor = successor;
    }

    public abstract void handleRequest(PurChaseRequest request);
}
