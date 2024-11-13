package com.omni.credentialmanagerchecking.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omni.credentialmanagerchecking.domain.model.SignInResult
import com.omni.credentialmanagerchecking.domain.repo.AuthRepository
import com.omni.credentialmanagerchecking.domain.usecase.LoginUseCase
import com.omni.credentialmanagerchecking.domain.usecase.LogoutUseCase
import com.omni.credentialmanagerchecking.ui.signup.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: LogoutUseCase,
) : ViewModel() {

    fun logoutUser() {
        viewModelScope.launch {
            useCase.invoke()
        }
    }

}