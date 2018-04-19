package com.example.asif.du_crs;

/**
 * Created by asif on 26-Mar-18.
 */

public class User {
    static User current=new User();
    String deptName =null , email=null , pass = null , uid=null ,name ;
    int accessCode = 0;

    public User()
    {
        deptName=email=pass=uid="Not Selected";
        accessCode = 0;
    }

    public User(String dept , String mail ,String n, String password , String id ,int code)
    {
        name = n;
        deptName = dept;
        email = mail;
        pass = password;
        uid = id;
        accessCode = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(int accessCode) {
        this.accessCode = accessCode;
    }

    public static User getCurrent() {
        return current;
    }

    public static void setCurrent(User current) {
        User.current = current;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
