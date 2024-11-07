package com.omni.credentialmanagerchecking.ui.login

import androidx.lifecycle.ViewModel
import com.omni.credentialmanagerchecking.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val useCase: LoginUseCase)
    : ViewModel() {

    suspend fun login(userName: String, password: String) {
        useCase.invoke(userName, password)
    }
}