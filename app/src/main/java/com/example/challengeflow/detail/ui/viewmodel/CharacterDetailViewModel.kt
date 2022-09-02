package com.example.challengeflow.detail.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengeflow.character.model.Character
import com.example.challengeflow.character.model.rest.toCharacter
import com.example.challengeflow.detail.usecases.GetFirstCharacterUseCase
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    private val getFirstCharacterUseCase: GetFirstCharacterUseCase
) : ViewModel() {

    private val _character = MutableLiveData<Character?>()
    val character: LiveData<Character?> = _character

    fun getFirstCharacter() {
        viewModelScope.launch {
            val response = getFirstCharacterUseCase()
            if (response.isSuccessful) {
                _character.postValue(response.body()?.toCharacter())
            } else _character.postValue(null)
        }
    }
}