package memento;

import java.util.ArrayList;

/**
 * 备忘录
 *
 * @see: Memento PACKAGE_NAME
 * @author: immDream
 * @since: 2022/04/22/12:11
 */
class Memento {
    private String account;
    private String password;
    private String telNo;

    Memento(String account, String password, String telNo) {
        this.account = account;
        this.password = password;
        this.telNo = telNo;
    }

    public String getAccount() {
        return account;
    }


    public String getPassword() {
        return password;
    }


    public String getTelNo() {
        return telNo;
    }
}
