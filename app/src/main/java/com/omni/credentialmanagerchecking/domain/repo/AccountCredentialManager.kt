package com.omni.credentialmanagerchecking.domain.repo

import android.content.Context
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPasswordOption
import androidx.credentials.PasswordCredential
import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.omni.credentialmanagerchecking.data.model.LoginResponse
import com.omni.credentialmanagerchecking.data.remote.LoginApi
import com.omni.credentialmanagerchecking.domain.model.SignInResult
import com.omni.credentialmanagerchecking.domain.model.SignUpResult
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class AccountCredentialManager @Inject constructor(
    @ActivityContext private val activity: Context,
    private val loginApi: LoginApi,
    private val dataProvider: DataProvider
) {
    private val credentialManager = CredentialManager.create(activity)

    suspend fun signUpWithPassword(username: String, password: String): SignUpResult {
        return try {
            val response = LoginResponse(true, "")
            return if (response.success) {
                credentialManager.createCredential(
                    context = activity,
                    request = CreatePasswordRequest(id = username, password = password)
                )
                dataProvider.setSignedIn(true)
                SignUpResult.Success(username)
            } else SignUpResult.Failure
        } catch (e: CreateCredentialCancellationException) {
            e.printStackTrace()
            SignUpResult.Cancelled
        } catch (e: CreateCredentialException) {
            e.printStackTrace()
            SignUpResult.Failure
        }
    }

    suspend fun signInWithPassword(): SignInResult {
        return try {
            val credentialResponse = credentialManager.getCredential(
                context = activity,
                request = configureGetCredentialRequest()
            )

            val credential = credentialResponse.credential as? PasswordCredential
                ?: return SignInResult.Failure
            handleSignIn(credential.id, credential.password)
        } catch (e: GetCredentialCancellationException) {
            e.printStackTrace()
            SignInResult.Cancelled
        } catch (e: NoCredentialException) {
            e.printStackTrace()
            SignInResult.NoCredentials
        } catch (e: GetCredentialException) {
            e.printStackTrace()
            SignInResult.Failure
        }
    }

     suspend fun handleSignIn(username: String, password: String): SignInResult {
        val response = LoginResponse(true, "")
//                loginApi.login(LoginRequest(result.username, result.password))
        return if (response.success) {
            dataProvider.setSignedIn(true)
            SignInResult.Success(username, password)
        } else SignInResult.Failure
    }

    private fun configureGetCredentialRequest(): GetCredentialRequest {
        val getPasswordOption = GetPasswordOption()
        val getCredentialRequest = GetCredentialRequest(listOf(getPasswordOption))
        return getCredentialRequest
    }

    suspend fun logoutUser() {
        dataProvider.setSignedIn(false)
    }
}