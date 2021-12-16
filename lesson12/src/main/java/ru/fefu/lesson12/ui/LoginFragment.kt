package ru.fefu.lesson12.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.onEach
import ru.fefu.lesson12.launchWhenStarted
import ru.fefu.lesson12.databinding.FragmentExampleBinding
import ru.fefu.lesson12.databinding.FragmentLoginBinding
import ru.fefu.lesson12.presentation.ExampleViewModel
import ru.fefu.lesson12.presentation.LoginViewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        with(binding) {

            viewModel.loaderVisible
                .onEach { showLoader(it) }
                .launchWhenStarted(viewLifecycleOwner.lifecycleScope)

            btnLogin.setOnClickListener {
                viewModel.onLoginButtonClicked(etLogin.text.toString(), etPassword.text.toString())
            }

            viewModel.dataFlow
                .onEach { binding.tvData.text = it }
                .launchWhenStarted(viewLifecycleOwner.lifecycleScope)
        }

        return binding.root
    }

    fun showLoader(show: Boolean) {
        binding.pbLoader.isVisible = show
    }

}