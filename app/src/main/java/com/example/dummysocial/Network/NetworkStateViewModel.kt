package com.example.dummysocial.Network

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NetworkStateViewModel : ViewModel() {
    private var _check: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var networkState: StateFlow<Boolean> = _check

    fun changeNetWorkState(state: Boolean) {
        _check.value = state
    }
}