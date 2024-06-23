package com.amitthecoder.timeup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class TimerViewModel : ViewModel() {
    private var timerJob: Job? = null
    private val _time = MutableLiveData<Long>(0)
    val time: LiveData<Long> = _time

    private val _isRunning = MutableLiveData(false)
    val isRunning: LiveData<Boolean> = _isRunning

    fun startOrPause() {
        if (timerJob?.isActive == true) {
            timerJob?.cancel()
            _isRunning.value = false
        } else {
            timerJob = CoroutineScope(Dispatchers.Main).launch {
                while (true) {
                    _time.value = (_time.value ?: 0) + 1000
                    delay(1000)
                }
            }
            _isRunning.value = true
        }
    }

    fun reset() {
        timerJob?.cancel()
        _time.value = 0
        _isRunning.value = false
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}