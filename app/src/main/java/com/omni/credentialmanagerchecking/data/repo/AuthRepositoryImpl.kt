package com.omni.credentialmanagerchecking.data.repo

import android.util.Log
import com.omni.credentialmanagerchecking.data.model.LoginRequest
import com.omni.credentialmanagerchecking.data.model.LoginResponse
import com.omni.credentialmanagerchecking.data.remote.LoginApi
import com.omni.credentialmanagerchecking.domain.repo.AccountCredentialManager
import com.omni.credentialmanagerchecking.domain.repo.DataProvider
import com.omni.credentialmanagerchecking.domain.model.SignInResult
import com.omni.credentialmanagerchecking.domain.model.SignUpResult
import com.omni.credentialmanagerchecking.domain.repo.AuthRepository
import com.omni.credentialmanagerchecking.domain.repo.TAG
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi,
    private val accountCredentialManager: AccountCredentialManager,
    private val dataProvider: DataProvider
) : AuthRepository {
    override suspend fun loginUser(): SignInResult {
        val result = accountCredentialManager.signInWithPassword()
        Log.d(TAG, "-------------- signInWithPassword result----> $result ------------ ")

        /**
         * login with the server after successful signIn with credential manager
         * */
        if (result is SignInResult.Success) {
            val response = LoginResponse(true,"")
            Log.d(TAG, "-------------- signInWithServer result----> ${response.success} ------------ ")
//                loginApi.login(LoginRequest(result.username, result.password))
            if (response.success)
                dataProvider.setSignedIn(true)
            else return SignInResult.Failure
        }

        return result
    }


    override suspend fun signUpUser(username: String, password: String): SignUpResult {
        val result = accountCredentialManager.signUpWithPassword(username, password)
        Log.d(TAG, "-------------- signUpWithPassword result----> $result ------------ ")

        /**
         * login with the server after successful signIn with credential manager
         * */
        if (result is SignUpResult.Success) {
            val response = LoginResponse(true,"")
            Log.d(TAG, "-------------- signSignUPWithServer result----> ${response.success} ------------ ")
//                loginApi.signUp(LoginRequest(username, password))
            if (response.success)
                dataProvider.setSignedIn(true)
            else return SignUpResult.Failure
        }
        return result
    }

    override fun isLoggedInUser(): Flow<Boolean> = dataProvider.isSignedIn

    override suspend fun logoutUser() {
        dataProvider.setSignedIn(false)
    }

//
//    override fun configureGetCredentialRequest() {
//        accountCredentialManager.configureGetCredentialRequest()
//    }
}