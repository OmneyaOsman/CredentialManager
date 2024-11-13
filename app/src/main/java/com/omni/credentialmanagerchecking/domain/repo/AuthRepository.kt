package com.omni.credentialmanagerchecking.domain.repo

import com.omni.credentialmanagerchecking.domain.model.SignInResult
import com.omni.credentialmanagerchecking.domain.model.SignUpResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun loginUser(): SignInResult
    suspend fun signUpUser(username: String, password: String): SignUpResult
    suspend fun logoutUser()
    fun isLoggedInUser(): Flow<Boolean>
//    fun configureGetCredentialRequest()
}