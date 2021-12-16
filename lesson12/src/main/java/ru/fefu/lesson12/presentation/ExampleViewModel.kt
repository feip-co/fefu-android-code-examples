package ru.fefu.lesson12.presentation

import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.fefu.lesson12.data.NetworkService
import ru.fefu.lesson12.data.Result
import ru.fefu.lesson12.data.repository.LoginRepository

class ExampleViewModel : ViewModel() {

    private val loginRepository: LoginRepository = LoginRepository(NetworkService())

    private val _loaderVisible = MutableStateFlow(false)

    val loaderVisible: StateFlow<Boolean>
        get() = _loaderVisible.asStateFlow()

    private val _dataFlow = MutableStateFlow("")

    val dataFlow: StateFlow<String>
        get() = _dataFlow.asStateFlow()

//    val liveData = liveData {
//        loginRepository.register("login22882", "test123", "test", 1)
//            .onStart { _loaderVisible.value = true }
//            .onCompletion { _loaderVisible.value = false }
//            .collect {
//                when (it) {
//                    is Result.Success<*> -> {
//                        emit(it.result)
//                    }
//                    is Result.Error<*> -> emit(it.e)
//                }
//            }
//    }

    fun onSaveButtonClicked(login: String, password: String, name: String, gender: Int) {
        viewModelScope.launch {
            loginRepository.register(login, password, name, gender)
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