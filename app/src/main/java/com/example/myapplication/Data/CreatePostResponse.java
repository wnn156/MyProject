package com.example.myapplication.Data;

import com.google.gson.annotations.SerializedName;

public class CreatePostResponse {
    @SerializedName("title")
    public String title;
    @SerializedName("author")
    public String author;
    @SerializedName("id")
    public String id;
    @SerializedName("body")
    public String body;
}
