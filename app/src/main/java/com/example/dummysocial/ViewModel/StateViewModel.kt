package com.example.dummysocial.ViewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StateViewModel: ViewModel() {

    private var _counter: MutableStateFlow<String> = MutableStateFlow("")
    var cnt: StateFlow<String> = _counter


    fun setValue(id : String){
        _counter.value = id
    }

}