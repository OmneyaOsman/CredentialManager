package com.omni.credentialmanagerchecking.domain.usecase

import com.omni.credentialmanagerchecking.domain.repo.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke() = repository.logoutUser()

}