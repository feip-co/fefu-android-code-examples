package ru.fefu.lesson12.presentation

import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.fefu.lesson12.data.NetworkService
import ru.fefu.lesson12.data.Result
import ru.fefu.lesson12.data.repository.LoginRepository

class LoginViewModel : ViewModel() {

    private val loginRepository: LoginRepository = LoginRepository(NetworkService())

    private val _loaderVisible = MutableStateFlow(false)

    val loaderVisible: StateFlow<Boolean>
        get() = _loaderVisible.asStateFlow()

    private val _dataFlow = MutableStateFlow("")

    val dataFlow: StateFlow<String>
        get() = _dataFlow.asStateFlow()


    fun onLoginButtonClicked(login: String, password: String) {
        viewModelScope.launch {
            loginRepository.login(login, password)
                .onStart { _loaderVisible.value = true }
                .onCompletion { _loaderVisible.value = false }
                .collect {
                    when (it) {
                        is Result.Success<*> -> _dataFlow.value = it.result.toString()
                        is Result.Error<*> -> _dataFlow.value = it.e.toString()
                    }
                }
        }
    }

}