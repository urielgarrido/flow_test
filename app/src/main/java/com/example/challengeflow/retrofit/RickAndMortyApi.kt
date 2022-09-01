package com.example.challengeflow.retrofit

import com.example.challengeflow.character.model.rest.CharacterRest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): Response<CharacterRest>
}