package com.appsport.model;

/**
 * @author LEBOC Philippe on 03/10/2016.
 */
public class Account {

    private String username;

    private String password;

    private String email;

    private long createDate;

    public Account(String username, String password, String email) {
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setCreateDate(System.currentTimeMillis());
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
