package com.example.challengeflow.detail.repository

import com.example.challengeflow.character.model.rest.ResultCharacterRest
import com.example.challengeflow.retrofit.RickAndMortyApi
import retrofit2.Response

class CharacterDetailRepository(
    private val rickAndMortyApi: RickAndMortyApi
) {

    suspend fun getFirstCharacter(): Response<ResultCharacterRest> {
        return rickAndMortyApi.getCharacterForId(1)
    }
}