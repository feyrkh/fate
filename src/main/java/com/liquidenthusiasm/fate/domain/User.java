package com.liquidenthusiasm.fate.domain;

import javax.persistence.Id;
import javax.persistence.Version;

/**
 * Created by Kevin on 10/16/2014.
 */
public class User {
    @Id
    private Object id;
    @Version
    private Object version;
    private String loginName;
    private String displayName;
    private String password;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
