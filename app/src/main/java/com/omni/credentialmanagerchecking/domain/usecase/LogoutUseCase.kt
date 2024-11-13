package com.omni.credentialmanagerchecking.domain.usecase

import com.omni.credentialmanagerchecking.domain.model.SignInResult
import com.omni.credentialmanagerchecking.domain.repo.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke() = repository.logoutUser()

}