package com.example.inhamind.Models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

public class Post {
    private String documentID;
    private String title;
    private String contents;
    private String studentID;
    private String status;
    @ServerTimestamp
    private Timestamp timestamp;

    public Post() {

    }

    public Post(String documentID, String title, String contents, String studentID, String status, Timestamp timestamp) {
        this.documentID = documentID;
        this.title = title;
        this.contents = contents;
        this.studentID = studentID;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Post{" +
                "documentID='" + documentID + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", studentID='" + studentID + '\'' +
                ", status=" + status +
                ", timestamp=" + timestamp +
                '}';
    }
}
