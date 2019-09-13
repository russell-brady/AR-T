package com.arproject.russell.ar_t.models;

import java.util.ArrayList;

public class GetClassesResponse {

    private int code;
    private String success;
    private ArrayList<Class> classes;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<Class> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<Class> classes) {
        this.classes = classes;
    }
}
