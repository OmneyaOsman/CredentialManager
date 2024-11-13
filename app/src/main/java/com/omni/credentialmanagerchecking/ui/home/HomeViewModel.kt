package com.omni.credentialmanagerchecking.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omni.credentialmanagerchecking.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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