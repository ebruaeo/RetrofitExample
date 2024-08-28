package com.example.retrofitexample

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // get users details
    @GET("users/{id}")
    fun getUserDetails(@Path("id") userId: Int): Call<User>

    // update user details
    @PUT("user/{id}")
    fun updateUserDetails(@Path("id") userId: Int, @Body user: User): Call<User>

    //Get list of users with query parameters
    @GET("users")
    fun getUsers(@Query("page") page: Int): Call<List<User>>

    //Create a new user
    @POST("user")
    fun createUser(@Body user: User): Call<User>

}