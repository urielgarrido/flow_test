package com.example.challengeflow.retrofit

import com.example.challengeflow.character.model.rest.CharacterRest
import retrofit2.Response
import retrofit2.http.GET

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacters(): Response<CharacterRest>
}