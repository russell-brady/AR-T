package com.arproject.russell.ar_t.models;

import android.os.Parcel;
import android.os.Parcelable;

public class AssignmentSubmission implements Parcelable {

    private int assignmentSubmissionId;
    private int assignmentId;
    private int userId;
    private String submission;
    private int id;
    private String name;
    private String email;
    private int grade;
    private String title;
    private String assignmentDesc;
    private String dateAssigned;
    private String dateDue;

    public AssignmentSubmission(int assignmentSubmissionId, int assignmentId, int userId, String submission, int id, String name, String email, int grade) {
        this.assignmentSubmissionId = assignmentSubmissionId;
        this.assignmentId = assignmentId;
        this.userId = userId;
        this.submission = submission;
        this.id = id;
        this.name = name;
        this.email = email;
        this.grade = grade;
    }

    protected AssignmentSubmission(Parcel in) {
        assignmentSubmissionId = in.readInt();
        assignmentId = in.readInt();
        userId = in.readInt();
        submission = in.readString();
        id = in.readInt();
        name = in.readString();
        email = in.readString();
        grade = in.readInt();
        title = in.readString();
        assignmentDesc = in.readString();
        dateAssigned = in.readString();
        dateDue = in.readString();
    }

    public static final Creator<AssignmentSubmission> CREATOR = new Creator<AssignmentSubmission>() {
        @Override
        public AssignmentSubmission createFromParcel(Parcel in) {
            return new AssignmentSubmission(in);
        }

        @Override
        public AssignmentSubmission[] newArray(int size) {
            return new AssignmentSubmission[size];
        }
    };

    public int getAssignmentSubmissionId() {
        return assignmentSubmissionId;
    }

    public void setAssignmentSubmissionId(int assignmentSubmissionId) {
        this.assignmentSubmissionId = assignmentSubmissionId;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSubmission() {
        return submission;
    }

    public void setSubmission(String submission) {
        this.submission = submission;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(assignmentSubmissionId);
        parcel.writeInt(assignmentId);
        parcel.writeInt(userId);
        parcel.writeString(submission);
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeInt(grade);
        parcel.writeString(title);
        parcel.writeString(assignmentDesc);
        parcel.writeString(dateAssigned);
        parcel.writeString(dateDue);
    }
}
