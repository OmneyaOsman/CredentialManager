package com.omni.credentialmanagerchecking.domain.usecase

import com.omni.credentialmanagerchecking.domain.model.SignInResult
import com.omni.credentialmanagerchecking.domain.repo.AccountCredentialManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val accountCredentialManager: AccountCredentialManager) {
   operator fun invoke(userName: String, password: String): Flow<SignInResult> =
        flow {
            emit(accountCredentialManager.handleSignIn(userName,password))
        }

}