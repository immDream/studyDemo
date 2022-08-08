import Role.Role;

/**
 * Created with IntelliJ IDEA.
 *
 * @see: 状态模式 PACKAGE_NAME
 * @author: 庄宇
 * @since: 2022/04/29/17:24
 */
public class Client {
    public static void main(String[] args) {
        Role role = new Role();
        role.setName("张三");
        role.play();
        role.play();
        role.play();
        role.play();
        role.play();
        role.play();
        role.play();
    }
}
