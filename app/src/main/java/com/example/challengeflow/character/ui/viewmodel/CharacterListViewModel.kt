package com.example.challengeflow.character.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.challengeflow.character.model.Character
import com.example.challengeflow.character.repository.CharacterDataSource


class CharacterListViewModel(
    private val characterDataSource: CharacterDataSource
) : ViewModel() {

    private val _loadingData = MutableLiveData<Boolean>()
    val loadingData: LiveData<Boolean> = _loadingData

    fun changeLoading(isLoading: Boolean) {
        _loadingData.value = isLoading
    }

    fun getCharactersPaging(): LiveData<PagingData<Character>> {
        return characterDataSource.getCharacters().cachedIn(viewModelScope)
    }

}