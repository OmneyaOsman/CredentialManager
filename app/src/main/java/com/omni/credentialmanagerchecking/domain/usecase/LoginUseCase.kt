package com.omni.credentialmanagerchecking.domain.usecase

import com.omni.credentialmanagerchecking.domain.model.SignInResult
import com.omni.credentialmanagerchecking.domain.repo.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke(): Flow<SignInResult> =
        flow {
            emit(repository.loginUser())
        }

}