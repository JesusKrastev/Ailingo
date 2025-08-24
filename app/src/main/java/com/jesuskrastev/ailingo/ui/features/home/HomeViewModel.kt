package com.jesuskrastev.ailingo.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesuskrastev.ailingo.data.TermRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val termRepository: TermRepository,
) : ViewModel() {
    private val _state = MutableStateFlow<HomeState>(HomeState())
    val state: StateFlow<HomeState> = _state

    private fun getTerm() {
        viewModelScope.launch {

            _state.value = HomeState(
                term = termRepository.get().toTermState(),
                isLoading = false,
            )
        }
    }

    init {
        getTerm()
    }
}