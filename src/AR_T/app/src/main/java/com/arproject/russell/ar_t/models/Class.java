package com.arproject.russell.ar_t.models;

public class Class {

    private String className;
    private int classId;
    private int teacherId;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return className;
    }

    public void setName(String name) {
        this.className = name;
    }

}
