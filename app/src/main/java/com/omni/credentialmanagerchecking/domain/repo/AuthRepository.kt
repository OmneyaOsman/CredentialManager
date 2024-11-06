package com.omni.credentialmanagerchecking.domain.repo

import com.omni.credentialmanagerchecking.domain.SignInResult
import com.omni.credentialmanagerchecking.domain.SignUpResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun loginUser(username: String, password: String): SignInResult
    suspend fun signUpUser(username: String, password: String): SignUpResult
    fun isLoggedInUser(): Flow<Boolean>
}