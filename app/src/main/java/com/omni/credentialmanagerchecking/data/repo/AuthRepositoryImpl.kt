package com.omni.credentialmanagerchecking.data.repo

import com.omni.credentialmanagerchecking.data.remote.LoginApi
import com.omni.credentialmanagerchecking.domain.repo.AccountCredentialManager
import com.omni.credentialmanagerchecking.domain.repo.DataProvider
import com.omni.credentialmanagerchecking.domain.model.SignInResult
import com.omni.credentialmanagerchecking.domain.model.SignUpResult
import com.omni.credentialmanagerchecking.domain.repo.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi,
    private val accountCredentialManager: AccountCredentialManager,
    private val dataProvider: DataProvider
) : AuthRepository {
    override suspend fun loginUser(): SignInResult {
        dataProvider.setSignedIn(true)
      return  accountCredentialManager.signInWithPassword()
    }


    override suspend fun signUpUser(username: String, password: String): SignUpResult {
//        loginApi.signUp(LoginRequest(username, password))
        dataProvider.setSignedIn(true)
       return accountCredentialManager.signUpWithPassword(username,password)

    }

    override fun isLoggedInUser(): Flow<Boolean> =  dataProvider.isSignedIn

    override suspend fun logoutUser(){
        dataProvider.setSignedIn(false)
    }


    override fun configureGetCredentialRequest() {
        accountCredentialManager.configureGetCredentialRequest()
    }
}