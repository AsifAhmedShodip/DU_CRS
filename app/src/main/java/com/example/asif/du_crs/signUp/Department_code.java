package com.example.asif.du_crs.signUp;

/**
 * Created by asif on 26-Mar-18.
 */

public class Department_code {
    String code,dept;
    Department_code()
    {
        dept = code = "Not Selected";
    }
    Department_code(String a ,String b)
    {
        dept = a;
        code = b;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
