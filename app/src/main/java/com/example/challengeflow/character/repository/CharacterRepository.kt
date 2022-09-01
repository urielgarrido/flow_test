package com.example.challengeflow.character.repository

import com.example.challengeflow.character.model.rest.CharacterRest
import com.example.challengeflow.retrofit.RickAndMortyApi
import retrofit2.Response

class CharacterRepository(
    private val api: RickAndMortyApi
) {

    suspend fun getCharacters(): Response<CharacterRest> {
        return api.getCharacters(1)
    }

    fun getCharactersPage() {
        return
    }
}