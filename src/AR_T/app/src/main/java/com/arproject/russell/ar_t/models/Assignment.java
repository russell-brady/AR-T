package com.arproject.russell.ar_t.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Assignment implements Parcelable {

    private int idAssignment;
    private int classId;
    private String title;
    private String assignmentDesc;
    private String dateAssigned;
    private String dateDue;
    private String submission;
    private Integer grade;

    public Assignment(Parcel in) {
        idAssignment = in.readInt();
        classId = in.readInt();
        title = in.readString();
        assignmentDesc = in.readString();
        dateAssigned = in.readString();
        dateDue = in.readString();
        submission = in.readString();
        if (in.readByte() == 0) {
            grade = null;
        } else {
            grade = in.readInt();
        }
    }

    public static final Creator<Assignment> CREATOR = new Creator<Assignment>() {
        @Override
        public Assignment createFromParcel(Parcel in) {
            return new Assignment(in);
        }

        @Override
        public Assignment[] newArray(int size) {
            return new Assignment[size];
        }
    };

    public Assignment(int idAssignment, int classId, String title, String assignmentDesc, String dateAssigned, String dateDue, String submission, Integer grade) {
        this.idAssignment = idAssignment;
        this.classId = classId;
        this.title = title;
        this.assignmentDesc = assignmentDesc;
        this.dateAssigned = dateAssigned;
        this.dateDue = dateDue;
        this.submission = submission;
        this.grade = grade;
    }

    public int getId() {
        return idAssignment;
    }

    public void setId(int id) {
        this.idAssignment = id;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAssignmentDesc() {
        return assignmentDesc;
    }

    public void setAssignmentDesc(String assignmentDesc) {
        this.assignmentDesc = assignmentDesc;
    }

    public String getDateAssigned() {
        return dateAssigned;
    }

    public void setDateAssigned(String dateAssigned) {
        this.dateAssigned = dateAssigned;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }

    public String getSubmission() {
        return submission;
    }

    public void setSubmission(String submission) {
        this.submission = submission;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idAssignment);
        parcel.writeInt(classId);
        parcel.writeString(title);
        parcel.writeString(assignmentDesc);
        parcel.writeString(dateAssigned);
        parcel.writeString(dateDue);
        parcel.writeString(submission);
        if (grade == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(grade);
        }
    }
}
