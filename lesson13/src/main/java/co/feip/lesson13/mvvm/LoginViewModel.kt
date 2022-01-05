package co.feip.lesson13.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.lesson13.model.LoginService
import co.feip.lesson13.model.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val loginService = LoginService()

    private val _showLoginError = MutableSharedFlow<Boolean>(replay = 0)
    private val _showPasswordError = MutableSharedFlow<Boolean>(replay = 0)
    private val _showLoading = MutableStateFlow(false)
    private val _result = MutableStateFlow("")
    private val _toast = MutableSharedFlow<String>(replay = 0)

    val showLoginError get() = _showLoginError
    val showPasswordError get() = _showPasswordError
    val showLoading get() = _showLoading
    val result get() = _result
    val toast get() = _toast


    fun onLoginClicked(login: String, password: String) {
        viewModelScope.launch {
            toast.emit("Hello")
            if (login.isBlank()) {
                _showLoginError.emit(true)
                return@launch
            }
            if (password.isBlank()) {
                _showPasswordError.emit(true)
                return@launch
            }
            delay(3000)
            loginService.login(login.trim(), password.trim())
                .onStart { _showLoading.value = true }
                .onCompletion { _showLoading.value = false }
                .collect {
                    when (it) {
                        is Result.Success -> _result.value = it.toString()
                        is Result.Error -> _result.value = it.e.toString()
                    }
                }
        }
    }

}