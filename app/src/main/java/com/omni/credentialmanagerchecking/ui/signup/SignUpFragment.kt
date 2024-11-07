package com.omni.credentialmanagerchecking.ui.signup

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
import com.omni.credentialmanagerchecking.databinding.FragmentSecondBinding
import com.omni.credentialmanagerchecking.domain.model.SignUpResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val signupViewModel: SignupViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onSignUpClicked()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                signupViewModel.signUpState.collect { state ->
                    viewsVisibility(state.inProgress)
                    binding.errorMessage.text = state.errorMessage
                    binding.username.setText(state.username)
                    binding.password.setText(state.errorMessage)
                    if (state.isRegister) {
                        findNavController().navigate(R.id.action_SecondFragment_to_homeFragment,Bundle().apply {
                            putString("username", state.loggedInUser)
                        })
                    }
                }
            }
        }
        binding.login.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun viewsVisibility(isVisible: Boolean) {
        binding.errorMessage.text = ""
        binding.circularProgressIndicator.isVisible = isVisible
        binding.textProgress.isVisible = isVisible
    }

    private fun onSignUpClicked() {
        binding.signUpWithPassword.setOnClickListener {
            if (binding.username.text.isNullOrEmpty()) {
                binding.username.error = "User name required"
                binding.username.requestFocus()
            } else if (binding.password.text.isNullOrEmpty()) {
                binding.password.error = "Password required"
                binding.password.requestFocus()
            } else {
                lifecycleScope.launch {
                    signupViewModel.signUpWithPassword(
                        binding.username.text.toString(),
                        binding.password.text.toString()
                    )

                    //                    simulateServerDelayAndLogIn()

                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}