package ru.fefu.lesson11.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.fefu.lesson11.network.NetworkService
import ru.fefu.lesson11.network.Result
import java.io.IOException

class TestViewModel : ViewModel() {

    private val network = NetworkService()

    private val _showLoader = MutableLiveData<Boolean>()
    private val _showError = MutableLiveData<Pair<Boolean, String>>()

    val showLoader: LiveData<Boolean>
        get() = _showLoader

    val showError: LiveData<Pair<Boolean, String>>
        get() = _showError

    val data = liveData(Dispatchers.Main) {
        val backOffTime = 3_000L
        var success = false
        while (!success){
            when(val data = network.getActivityTypes("борд")){
                is Result.Success -> {
                    emit(data.result)
                    success = true
                    _showError.postValue(false to "")
                }
                is Result.Error -> {
                    delay(backOffTime)
                    emit(listOf())
                    _showError.postValue(true to data.e.toString())
                }
            }
        }
    }

}