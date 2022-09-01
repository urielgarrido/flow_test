package com.example.challengeflow.character.usecase

import com.example.challengeflow.character.model.rest.CharacterRest
import com.example.challengeflow.character.repository.CharacterRepository
import retrofit2.Response

class GetCharactersUseCase(private val repository: CharacterRepository) {
    suspend operator fun invoke(): Response<CharacterRest> {
        return repository.getCharacters()
    }
}