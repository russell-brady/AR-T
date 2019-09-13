package com.arproject.russell.ar_t.utils;

import com.arproject.russell.ar_t.models.ARResponse;
import com.arproject.russell.ar_t.models.ApiResponse;
import com.arproject.russell.ar_t.models.AssignmentSubmissionResponse;
import com.arproject.russell.ar_t.models.CloudAnchorResponse;
import com.arproject.russell.ar_t.models.GetAssignmentsResponse;
import com.arproject.russell.ar_t.models.GetClassesResponse;
import com.arproject.russell.ar_t.models.GetStudentsResponse;
import com.arproject.russell.ar_t.models.LessonResponse;
import com.arproject.russell.ar_t.models.QuizResponse;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @POST("registerTeacher")
    @FormUrlEncoded
    Call<ApiResponse> performTeacherRegistration(@Field("email") String email, @Field("password") String password, @Field("type") String type, @Field("name") String name);

    @POST("registerStudent")
    @FormUrlEncoded
    Call<ApiResponse> performStudentRegistration(@Field("email") String email, @Field("password") String password, @Field("type") String type, @Field("name") String name, @Field("classId") int classId);

    @POST("login")
    @FormUrlEncoded
    Call<ApiResponse> performLogin(@Field("email") String email, @Field("password") String password);

    @POST("createClass")
    @FormUrlEncoded
    Call<ApiResponse> createClass(@Field("userId") int userId, @Field("className") String className, @Field("classId") int classId);

    @POST("getClasses")
    @FormUrlEncoded
    Call<GetClassesResponse> getClasses(@Field("id") int id);

    @POST("getClassStudents")
    @FormUrlEncoded
    Call<GetStudentsResponse> getClassStudents(@Field("classId") int classId);

    @POST("getAssignments")
    @FormUrlEncoded
    Call<GetAssignmentsResponse> getClassAssignments(@Field("classId") int classId);

    @POST("createAssignment")
    @FormUrlEncoded
    Call<ApiResponse> createAssignment(@Field("classId") int classId, @Field("title") String title, @Field("desc") String assignmentDesc, @Field("dateAssigned") String dateAssigned, @Field("dateDue") String dateDue);

    @POST("getStudentAssignments")
    @FormUrlEncoded
    Call<GetAssignmentsResponse> getStudentAssignments(@Field("id") int id);

    @POST("getClassmates")
    @FormUrlEncoded
    Call<GetStudentsResponse> getClassmates(@Field("id") int id);

    @POST("assignmentSubmission")
    @FormUrlEncoded
    Call<ApiResponse> assignmentSubmission(@Field("assignmentId") int assignmentId, @Field("userId") int userId, @Field("submission") String submission);

    @POST("getAssignmentSubmissions")
    @FormUrlEncoded
    Call<AssignmentSubmissionResponse> getAssignmentSubmissions(@Field("assignmentId") int assignmentId);

    @POST("submitGrade")
    @FormUrlEncoded
    Call<ApiResponse> submitGrade(@Field("assignmentSubmissionId") int assignmentSubmissionId, @Field("grade") int grade);

    @POST("addCloudAnchorId")
    @FormUrlEncoded
    Call<CloudAnchorResponse> addCloudAnchor(@Field("anchor_id") String anchor_id, @Field("model_id") int model_id);

    @POST("getCloudAnchorId")
    @FormUrlEncoded
    Call<CloudAnchorResponse> getCloudAnchor(@Field("anchor_key") int anchor_key);

    @GET("getModels/{userId}")
    Call<ARResponse> getARModels(@Path("userId") int userId);

    @GET("getLessonsCompleted/{userId}")
    Call<LessonResponse> getLessonsCompleted(@Path("userId") int userId);

    @POST("setLessonsCompleted")
    @FormUrlEncoded
    Call<ApiResponse> setLessonsCompleted(@Field("userId") int userId, @Field("lessonId") int lessonId);

    @GET("getStudentSubmissions/{id}")
    Call<AssignmentSubmissionResponse> getStudentSubmissions(@Path("id") int id);

    @POST("setQuizCompleted")
    @FormUrlEncoded
    Call<ApiResponse> setQuizCompleted(@Field("userId") int userId, @Field("quizId") int quizId, @Field("quizScore") int quizScore);

    @GET("getQuizzesCompleted/{userId}")
    Call<QuizResponse> getQuizzesCompleted(@Path("userId") int userId);

}
