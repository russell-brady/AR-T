package com.arproject.russell.ar_t.models;

import java.util.ArrayList;

public class ARResponse {

    private int code;
    private String success;
    private ArrayList<ARModel> models;

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

    public ArrayList<ARModel> getModels() {
        return models;
    }

    public void setModels(ArrayList<ARModel> models) {
        this.models = models;
    }
}
