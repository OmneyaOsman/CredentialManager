package com.omni.credentialmanagerchecking.data.repo

import com.omni.credentialmanagerchecking.data.model.LoginRequest
import com.omni.credentialmanagerchecking.data.remote.LoginApi
import com.omni.credentialmanagerchecking.domain.AccountCredentialManager
import com.omni.credentialmanagerchecking.domain.DataProvider
import com.omni.credentialmanagerchecking.domain.SignInResult
import com.omni.credentialmanagerchecking.domain.SignUpResult
import com.omni.credentialmanagerchecking.domain.repo.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi,
    private val accountCredentialManager: AccountCredentialManager,
    private val dataProvider: DataProvider
) : AuthRepository {
    override suspend fun loginUser(username: String, password: String): SignInResult {
        dataProvider.setSignedIn(true)
      return  accountCredentialManager.signIn()
    }


    override suspend fun signUpUser(username: String, password: String): SignUpResult {
//        loginApi.signUp(LoginRequest(username, password))
        dataProvider.setSignedIn(true)
       return accountCredentialManager.signUpWithPassword(username,password)

    }

    override fun isLoggedInUser(): Flow<Boolean> =  dataProvider.isSignedIn
}