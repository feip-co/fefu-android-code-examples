package co.feip.lesson13.mvp

import co.feip.lesson13.model.LoginService
import co.feip.lesson13.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginPresenter {

    private var view: LoginView? = null

    private val coroutineScope = CoroutineScope(SupervisorJob())
    private val loginService = LoginService()

    fun attachView(view: LoginView) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    fun onLoginClicked(login: String, password: String) {
        if (login.isBlank()) {
            view?.showLoginError()
            return
        }
        if (password.isBlank()) {
            view?.showPasswordError()
            return
        }
        view?.showLoading(true)
        coroutineScope.launch {
            delay(3000)
            loginService.login(login, password).collect {
                launch(Dispatchers.Main) {
                    view?.showLoading(false)
                    when (it) {
                        is Result.Success -> view?.showResult(it.toString())
                        is Result.Error -> view?.showResult(it.e.toString())
                    }
                }
            }
        }
    }

}