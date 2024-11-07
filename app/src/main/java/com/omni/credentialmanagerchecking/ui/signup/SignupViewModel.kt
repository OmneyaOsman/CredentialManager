package com.omni.credentialmanagerchecking.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omni.credentialmanagerchecking.domain.model.SignUpResult
import com.omni.credentialmanagerchecking.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val useCase: SignUpUseCase) : ViewModel() {

    private val _signUpState = MutableStateFlow(LoginState())
    val signUpState = _signUpState.asStateFlow()


    fun signUpWithPassword(userName: String, password: String) {
        viewModelScope.launch {
            useCase.invoke(userName, password).onStart {
                _signUpState.update {
                    it.copy(inProgress = true)
                }
            }.collect { state ->
                when (state) {
                    SignUpResult.Cancelled -> {
                        _signUpState.update {
                            it.copy(errorMessage = "SignUp Cancelled", inProgress = false)
                        }
                    }

                    SignUpResult.Failure -> {
                        _signUpState.update {
                            it.copy(errorMessage = "SignUp Failed", inProgress = false)
                        }
                    }

                    is SignUpResult.Success -> _signUpState.update {
                        it.copy(
                            loggedInUser = userName,
                            inProgress = false,
                            isRegister = true,
                            errorMessage = ""
                        )
                    }
                }


            }
        }
    }
}

data class LoginState(
    val loggedInUser: String? = null,
    val errorMessage: String? = null,
    val isRegister: Boolean = false,
    val inProgress: Boolean = false,
    val username: String = "user",
    val password: String = "pass",
)