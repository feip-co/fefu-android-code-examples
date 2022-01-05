package co.feip.lesson13.mvp.moxy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import co.feip.lesson13.R
import co.feip.lesson13.databinding.FragmentLoginBinding
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class LoginFragment : MvpAppCompatFragment(R.layout.fragment_login), LoginView {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val presenter by moxyPresenter { LoginPresenter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)?.also {
            _binding = FragmentLoginBinding.bind(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            presenter.onLoginClicked(
                binding.etLogin.text.toString(),
                binding.etPassword.text.toString()
            )
        }
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