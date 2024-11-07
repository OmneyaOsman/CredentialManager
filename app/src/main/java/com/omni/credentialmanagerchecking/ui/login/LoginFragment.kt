package com.omni.credentialmanagerchecking.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.omni.credentialmanagerchecking.R
import com.omni.credentialmanagerchecking.databinding.FragmentFirstBinding
import com.omni.credentialmanagerchecking.domain.model.SignUpResult
import com.omni.credentialmanagerchecking.ui.signup.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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

        onSignUpClicked()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED)
            {
                loginViewModel.signInState.collect { state ->
                    viewsVisibility(state.inProgress)
                    binding.errorMessage.text = state.errorMessage
                    if (state.isRegister) {
                        findNavController().navigate(R.id.action_FirstFragment_to_homeFragment , Bundle().apply {
                            putString("username", state.loggedInUser)
                        })
                    }
                }
            }
        }
        binding.signup.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

    }

    private fun viewsVisibility(isVisible: Boolean) {
        binding.errorMessage.text = ""
        binding.circularProgressIndicator.isVisible = isVisible
        binding.textProgress.isVisible = isVisible
    }

    private fun onSignUpClicked() {
        binding.signUpWithPassword.setOnClickListener {
                lifecycleScope.launch {
                    loginViewModel.signInWithPassword()

                    //                    simulateServerDelayAndLogIn()

                }
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}