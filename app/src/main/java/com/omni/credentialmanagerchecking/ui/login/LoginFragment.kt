package com.omni.credentialmanagerchecking.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPasswordOption
import androidx.credentials.GetPublicKeyCredentialOption
import androidx.credentials.PasswordCredential
import androidx.credentials.PendingGetCredentialRequest
import androidx.credentials.PublicKeyCredential
import androidx.credentials.pendingGetCredentialRequest
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.omni.credentialmanagerchecking.R
import com.omni.credentialmanagerchecking.databinding.FragmentFirstBinding
import com.omni.credentialmanagerchecking.domain.repo.TAG
import com.omni.credentialmanagerchecking.ui.signup.LoginState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val loginViewModel: LoginViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleSignInState()
        onSignInClicked()
        navigateToSignUpWhenSignUpClicked()

        val getCredentialRequest = configureGetCredentialRequest()
        configureAutofill(getCredentialRequest)

    }

    private fun configureAutofill(getCredentialRequest: GetCredentialRequest) {
        binding.textUsername.pendingGetCredentialRequest =
            pendingGetCredentialRequest(getCredentialRequest)
    }

    private fun pendingGetCredentialRequest(getCredentialRequest: GetCredentialRequest) =
        PendingGetCredentialRequest(
            getCredentialRequest
        ) { response ->
            if (response.credential is PasswordCredential) {
                val credential = response.credential as PasswordCredential
                loginViewModel.login(credential.id , credential.password)
            }
        }

    private fun configureGetCredentialRequest(): GetCredentialRequest {
        val getPasswordOption = GetPasswordOption()
        val getCredentialRequest = GetCredentialRequest(
            listOf(
                getPasswordOption
            )
        )
        return getCredentialRequest
    }

    private fun handleSignInState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED)
            {
                loginViewModel.signInState.collect { state ->
                    viewsVisibility(state.inProgress)
                    binding.errorMessage.text = state.errorMessage
                    navigateToHome(state)
                }
            }
        }
    }

    private fun navigateToSignUpWhenSignUpClicked() {
        binding.signup.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun navigateToHome(state: LoginState) {
        if (state.isRegister) {
            findNavController().navigate(R.id.action_FirstFragment_to_homeFragment, Bundle().apply {
                putString("username", state.loggedInUser)
            })
        }
    }

    private fun viewsVisibility(isVisible: Boolean) {
        binding.errorMessage.text = ""
        binding.circularProgressIndicator.isVisible = isVisible
        binding.textProgress.isVisible = isVisible
    }

    private fun onSignInClicked() {
        binding.signInWithPassword.setOnClickListener {
                lifecycleScope.launch {
                    loginViewModel.signInWithPassword()
                }
            }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}