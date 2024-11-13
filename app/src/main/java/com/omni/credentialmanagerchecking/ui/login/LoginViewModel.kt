package com.omni.credentialmanagerchecking.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omni.credentialmanagerchecking.domain.model.SignInResult
import com.omni.credentialmanagerchecking.domain.usecase.LoginUseCase
import com.omni.credentialmanagerchecking.domain.usecase.LoginWithSavedCredentialsUseCase
import com.omni.credentialmanagerchecking.ui.signup.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val loginWithSavedCredentialsUseCase: LoginWithSavedCredentialsUseCase
) : ViewModel() {

    var signInState = MutableStateFlow(LoginState())


    fun signInWithPassword() {
        viewModelScope.launch {
            signInState.update { it.copy(inProgress = true) }
            val result = loginWithSavedCredentialsUseCase.invoke()
            handleLoginResult(result)
        }
    }

    private suspend fun handleLoginResult(result: Flow<SignInResult>) {
        result.collect { state ->
            when (state) {
                SignInResult.Cancelled -> {
                    signInState.update {
                        it.copy(errorMessage = "SignIn Cancelled", inProgress = false)
                    }
                }

                SignInResult.Failure -> {
                    signInState.update {
                        it.copy(errorMessage = "SignIn Failed", inProgress = false)
                    }
                }

                is SignInResult.Success -> signInState.update {
                    it.copy(
                        loggedInUser = state.username,
                        inProgress = false,
                        isRegister = true,
                        errorMessage = ""
                    )
                }

                SignInResult.NoCredentials -> signInState.update {
                    it.copy(errorMessage = "No Credentials", inProgress = false)
                }
            }

        }
    }

    fun login(userName: String, password: String) {
        viewModelScope.launch {
            signInState.update { it.copy(inProgress = true) }
            val result = loginUseCase.invoke(userName, password)
            handleLoginResult(result)
        }

    }
}