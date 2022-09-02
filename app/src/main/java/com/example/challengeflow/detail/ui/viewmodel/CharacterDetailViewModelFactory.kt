package com.example.challengeflow.detail.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.challengeflow.detail.usecases.GetFirstCharacterUseCase

class CharacterDetailViewModelFactory(
    private val getFirstCharacterUseCase: GetFirstCharacterUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterDetailViewModel(getFirstCharacterUseCase) as T
    }
}