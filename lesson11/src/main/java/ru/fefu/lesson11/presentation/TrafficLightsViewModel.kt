package ru.fefu.lesson11.presentation

import androidx.lifecycle.*
import kotlinx.coroutines.*
import ru.fefu.lesson11.network.NetworkService
import kotlin.random.Random

class TrafficLightsViewModel : ViewModel() {

    private val network = NetworkService()
    private var timerJob: Job = SupervisorJob()

    private val _showTimer = MutableLiveData<Boolean>()
    private val _timerValue = MutableLiveData<Int>()
    private val _randomColor = MutableLiveData<String>()
    private val _randomLineColor = MutableLiveData<Triple<String, String, String>>()
    private val _showLoader = MutableLiveData<Boolean>()
    private val rightItem = MutableLiveData<Int>()

    val randomColor: LiveData<String>
        get() = _randomColor

    val randomLineColor: LiveData<Triple<String, String, String>>
        get() = _randomLineColor

    val timerValue: LiveData<Int>
        get() = _timerValue

    val showTimer: LiveData<Boolean>
        get() = _showTimer

    val showLoader: LiveData<Boolean>
        get() = _showLoader

    init {
        updateColor()
    }

    fun onColorItemClicked(item: Int) {
        timerJob.cancel()
        _showTimer.postValue(false)
        _showLoader.postValue(false)
        if (rightItem.value!! == item) {
            updateColor()
        }
    }

    private fun updateColor() {
        viewModelScope.launch {
            _showLoader.postValue(true)
            val data =
                Triple(network.getRandomColor(), network.getRandomColor(), network.getRandomColor())
            rightItem.value = Random.nextInt(2)
            _randomLineColor.postValue(data)
            _randomColor.postValue(data.toList()[rightItem.value!!])
            startTimer()
        }
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            _showTimer.postValue(true)
            for (i in Random.nextInt(1, 4) downTo 0) {
                _timerValue.postValue(i)
                delay(1_000)
            }
            _showTimer.postValue(false)
            _showLoader.postValue(false)
        }
    }


}