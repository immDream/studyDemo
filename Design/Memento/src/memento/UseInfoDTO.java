package memento;

import memento.Memento;


/**
 * 原发器
 *
 * @see: Memento PACKAGE_NAME
 * @author: immDream
 * @since: 2022/04/22/12:08
 */
public class UseInfoDTO {
    private String account;
    private String password;
    private String telNo;

    public UseInfoDTO() {}

    public UseInfoDTO(String account, String password, String telNo) {
        this.account = account;
        this.password = password;
        this.telNo = telNo;
    }

    /**
     * 保存到备忘录
     * @return
     */
    public Memento saveMemento() {
        return new Memento(account, password, telNo);
    }

    /**
     * 从备忘录恢复，数据不存在则不恢复
     * @param memento
     */
    public void restoreMemento(Memento memento) {
        if(memento == null) return;
        this.account = memento.getAccount();
        this.password = memento.getPassword();
        this.telNo = memento.getTelNo();
    }

    public void show() {
        System.out.println("Account:" + this.account);
        System.out.println("Password:" + this.password);
        System.out.println("TelNo:" + this.telNo);
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }
}
