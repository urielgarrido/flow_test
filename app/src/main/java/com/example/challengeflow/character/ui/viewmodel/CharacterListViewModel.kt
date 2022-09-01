package com.example.challengeflow.character.ui.viewmodel

import androidx.lifecycle.*
import com.example.challengeflow.character.model.Character
import com.example.challengeflow.character.model.rest.toCharacter
import com.example.challengeflow.character.usecase.GetCharactersUseCase
import kotlinx.coroutines.launch


class CharacterListViewModel(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private fun setError(errorMessage: String) {
        _error.postValue(errorMessage)
    }

    private val _characters = MutableLiveData<List<Character>>()
    val characters: MutableLiveData<List<Character>> = _characters

    private fun setCharacters(characterList: List<Character>) {
        _characters.postValue(characterList)
    }

    private val _loadingData = MutableLiveData<Boolean>()
    val loadingData: LiveData<Boolean> = _loadingData

    private fun changeLoading(isLoading: Boolean) {
        _loadingData.value = isLoading
    }

    fun getCharacters() {
        viewModelScope.launch {
            changeLoading(true)
            val response = getCharactersUseCase()
            if (response.isSuccessful) {
                val results = response.body()?.results
                results?.let { charactersResults ->
                    if (charactersResults.isEmpty()) {
                        setCharacters(emptyList())
                    } else {
                        val charactersMapped = charactersResults.map {
                            it.toCharacter()
                        }
                        setCharacters(charactersMapped)
                    }
                }
            } else {
                val errorMessage = response.message()
                setError(errorMessage)
            }
            changeLoading(false)
        }
    }
}

class CharacterViewModelFactory(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterListViewModel(getCharactersUseCase) as T
    }
}