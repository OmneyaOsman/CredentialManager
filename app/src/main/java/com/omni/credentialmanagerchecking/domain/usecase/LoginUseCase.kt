package com.omni.credentialmanagerchecking.domain.usecase

import com.omni.credentialmanagerchecking.domain.SignInResult
import com.omni.credentialmanagerchecking.domain.repo.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): SignInResult =
        repository.loginUser(username, password)
}