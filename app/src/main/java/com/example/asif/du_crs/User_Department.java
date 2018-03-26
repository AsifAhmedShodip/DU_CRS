package com.example.asif.du_crs;

/**
 * Created by asif on 26-Mar-18.
 */

public class User_Department {
    static User_Department current_Dept=new User_Department();
    String deptName =null , email=null , pass = null , uid=null ;

    public User_Department()
    {
        deptName=email=pass=uid="Not Selected";
    }

    public User_Department(String dept , String mail , String password , String id)
    {
        deptName = dept;
        email = mail;
        pass = password;
        uid = id;
    }

    public static User_Department getCurrent_Dept() {
        return current_Dept;
    }

    public static void setCurrent_Dept(User_Department current_Dept) {
        User_Department.current_Dept = current_Dept;
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
