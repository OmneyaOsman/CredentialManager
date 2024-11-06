package com.omni.credentialmanagerchecking.domain.usecase

import com.omni.credentialmanagerchecking.domain.SignUpResult
import com.omni.credentialmanagerchecking.domain.repo.AuthRepository

class SignUpUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): SignUpResult =
        repository.signUpUser(username, password)
}