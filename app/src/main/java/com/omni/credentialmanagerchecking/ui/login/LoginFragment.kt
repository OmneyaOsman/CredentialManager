package com.omni.credentialmanagerchecking.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.omni.credentialmanagerchecking.databinding.FragmentFirstBinding
import com.omni.credentialmanagerchecking.ui.signup.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val signupViewModel: SignupViewModel by viewModels ()

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