package com.omni.credentialmanagerchecking.domain.usecase

import com.omni.credentialmanagerchecking.domain.repo.AccountCredentialManager
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val accountCredentialManager: AccountCredentialManager) {
    suspend operator fun invoke() = accountCredentialManager.logoutUser()

}