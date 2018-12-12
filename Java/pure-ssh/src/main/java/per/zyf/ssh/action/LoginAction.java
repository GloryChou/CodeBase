package per.zyf.ssh.action;

import javax.swing.*;

public class LoginAction implements Action {
    // 参数
    private String username;
    private String password;

    public String login() throws Exception {
        if (username.equals("admin") && password.equals("admin123")) {
            return "success";
        } else {
            return "error";
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
