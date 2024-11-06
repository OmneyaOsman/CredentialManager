package com.omni.credentialmanagerchecking.ui.signup

import androidx.lifecycle.ViewModel
import com.omni.credentialmanagerchecking.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val useCase: SignUpUseCase)
    : ViewModel() {

    suspend fun signUpWithPassword(userName: String, password: String) {
        useCase.invoke(userName, password)
    }
}