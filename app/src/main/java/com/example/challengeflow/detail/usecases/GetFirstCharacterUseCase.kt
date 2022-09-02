package com.example.challengeflow.detail.usecases

import com.example.challengeflow.character.model.rest.ResultCharacterRest
import com.example.challengeflow.detail.repository.CharacterDetailRepository
import retrofit2.Response

class GetFirstCharacterUseCase(
    private val repository: CharacterDetailRepository
) {

    suspend operator fun invoke(): Response<ResultCharacterRest> {
        return repository.getFirstCharacter()
    }
}