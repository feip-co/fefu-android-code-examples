package co.feip.lesson13.mvvm

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import co.feip.lesson13.BaseFragment
import co.feip.lesson13.R
import co.feip.lesson13.databinding.FragmentLoginBinding
import co.feip.lesson13.launchWhenStarted
import kotlinx.coroutines.flow.onEach

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val viewModel by lazy { ViewModelProvider(this)[LoginViewModel::class.java] }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.showLoading
            .onEach { binding.pbLoader.isVisible = it }
            .launchWhenStarted(lifecycleScope)

        viewModel.showLoginError
            .onEach { binding.etLogin.error = "Введите логин" }
            .launchWhenStarted(lifecycleScope)
        viewModel.showPasswordError
            .onEach { binding.etPassword.error = "Введите пароль" }
            .launchWhenStarted(lifecycleScope)
        viewModel.result
            .onEach { binding.tvData.text = it }
            .launchWhenStarted(lifecycleScope)

        viewModel.toast
            .onEach { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
            .launchWhenStarted(lifecycleScope)

        binding.btnLogin.setOnClickListener {
            viewModel.onLoginClicked(
                binding.etLogin.text.toString(),
                binding.etPassword.text.toString()
            )
        }
    }

}