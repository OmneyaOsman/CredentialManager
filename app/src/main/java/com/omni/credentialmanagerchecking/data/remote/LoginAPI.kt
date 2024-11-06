package com.omni.credentialmanagerchecking.data.remote

import com.omni.credentialmanagerchecking.data.model.LoginRequest
import com.omni.credentialmanagerchecking.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface LoginApi {
    @POST("/api/signin")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("/api/signup")
    suspend fun signUp(@Body request: LoginRequest): LoginResponse
}