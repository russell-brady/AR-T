package com.arproject.russell.ar_t.models;

public class CloudAnchorResponse {

    private int code;
    private String success;
    private int anchorKey;
    private String anchorId;
    private String modelLocation;

    public String getModelLocation() {
        return modelLocation;
    }

    public void setModelLocation(String modelLocation) {
        this.modelLocation = modelLocation;
    }

    public String getAnchorId() {
        return anchorId;
    }

    public void setAnchorId(String anchorId) {
        this.anchorId = anchorId;
    }

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

    public int getAnchorKey() {
        return anchorKey;
    }

    public void setAnchorKey(int anchorKey) {
        this.anchorKey = anchorKey;
    }
}
