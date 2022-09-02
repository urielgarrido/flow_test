package com.example.challengeflow.character.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.challengeflow.character.repository.CharacterDataSource

class CharacterViewModelFactory(
    private val characterDataSource: CharacterDataSource
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterListViewModel(characterDataSource) as T
    }
}