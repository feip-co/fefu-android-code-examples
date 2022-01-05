package co.feip.lesson13.mvc

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import co.feip.lesson13.BaseFragment
import co.feip.lesson13.R
import co.feip.lesson13.databinding.FragmentLoginBinding
import co.feip.lesson13.model.LoginService
import co.feip.lesson13.model.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val loginService by lazy { LoginService() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener { login() }
    }

    private fun login() {
        val login = binding.etLogin.text.toString()
        val password = binding.etPassword.text.toString()

        if (login.isBlank()) {
            binding.etLogin.error = "Введите логин"
            return
        }
        if (password.isBlank()) {
            binding.etPassword.error = "Введите пароль"
            return
        }

        showLoading(true)
        lifecycleScope.launch {
            delay(3000)
            loginService.login(login.trim(), password.trim()).collect {
                showLoading(false)
                when (it) {
                    is Result.Success -> binding.tvData.text = it.toString()
                    is Result.Error -> binding.tvData.text = it.e.toString()
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.pbLoader.isVisible = show
    }

}