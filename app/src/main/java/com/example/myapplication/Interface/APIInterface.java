package com.example.myapplication.Interface;

import com.example.myapplication.Data.MultipleResource;
import com.example.myapplication.Data.Post;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {

    @GET("test/posts")
    Call<MultipleResource> doGetListResources();

    @POST("test/posts")
    Call<Post> createPost(@Body Post post);

}