package com.omni.credentialmanagerchecking.domain.usecase

import com.omni.credentialmanagerchecking.domain.model.SignUpResult
import com.omni.credentialmanagerchecking.domain.repo.AccountCredentialManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val accountCredentialManager: AccountCredentialManager) {
     operator fun invoke(username: String, password: String): Flow<SignUpResult> =
        flow {
            emit(accountCredentialManager.signUpWithPassword(username, password))
        }

}