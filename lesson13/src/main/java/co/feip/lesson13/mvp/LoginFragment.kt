package co.feip.lesson13.mvp

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import co.feip.lesson13.BaseFragment
import co.feip.lesson13.R
import co.feip.lesson13.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login), LoginView {

    private val presenter = LoginPresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)

        binding.btnLogin.setOnClickListener {
            presenter.onLoginClicked(
                binding.etLogin.text.toString(),
                binding.etPassword.text.toString()
            )
        }
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    override fun showLoading(show: Boolean) {
        binding.pbLoader.isVisible = show
    }

    override fun showLoginError() {
        binding.etLogin.error = "Введите логин"
    }

    override fun showPasswordError() {
        binding.etPassword.error = "Введите пароль"
    }

    override fun showResult(result: String) {
        binding.tvData.text = result
    }

}