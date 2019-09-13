package com.arproject.russell.ar_t.models;

import java.util.Objects;

public class ApiResponse {

    private int code;
    private String success;
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiResponse that = (ApiResponse) o;
        return code == that.code &&
                Objects.equals(success, that.success) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(code, success, user);
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "code=" + code +
                ", success='" + success + '\'' +
                ", user=" + user +
                '}';
    }
}
