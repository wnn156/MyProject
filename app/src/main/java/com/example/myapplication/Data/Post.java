package com.example.myapplication.Data;

import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("title")
    public String title;
    @SerializedName("author")
    public String author;
    @SerializedName("_id")
    public String id;
    @SerializedName("body")
    public String body;

    public Post(){
        title = "default";
        author = "default";
        body = "default";
    }
    public Post(String title, String author, String body){
        this.title = title;
        this.author = author;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", id='" + id + '\'' +
                ", body='" + body + '\'' +
                '}' + "\n";
    }
}