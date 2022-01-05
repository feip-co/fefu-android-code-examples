package co.feip.lesson13.mvp.moxy

import co.feip.lesson13.model.LoginService
import co.feip.lesson13.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import moxy.MvpPresenter

class LoginPresenter : MvpPresenter<LoginView>() {

    private val coroutineScope = CoroutineScope(SupervisorJob())
    private val loginService = LoginService()

    fun onLoginClicked(login: String, password: String) {
        if (login.isBlank()) {
            viewState.showLoginError()
            return
        }
        if (password.isBlank()) {
            viewState.showPasswordError()
            return
        }
        viewState.showLoading(true)
        coroutineScope.launch {
            delay(3000)
            loginService.login(login, password).collect {
                launch(Dispatchers.Main) {
                    viewState.showLoading(false)
                    when (it) {
                        is Result.Success -> viewState.showResult(it.toString())
                        is Result.Error -> viewState.showResult(it.e.toString())
                    }
                }
            }
        }
    }

}