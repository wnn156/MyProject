package com.example.myapplication.Interface;

import com.example.myapplication.Data.Post;
import com.example.myapplication.Data.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {

    final String Base_URL = "http://35.208.218.32:3000/";

    @GET("/test/posts")
    Call<ArrayList<Post>> getListPosts();

    @GET("/test/users")
    Call<ArrayList<User>> getListUsers();

    @GET("/test/users/{user}")
    Call<User> getUser(@Path("user")String id);

    @POST("test/posts")
    Call<Post> createPost(@Body Post post);

    @POST("test/users")
    Call<User> createUser(@Body User user);
}