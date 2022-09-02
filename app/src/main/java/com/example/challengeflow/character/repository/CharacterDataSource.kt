package com.example.challengeflow.character.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.challengeflow.character.model.Character
import com.example.challengeflow.retrofit.RickAndMortyApi

private const val PAGE_SIZE = 20

class CharacterDataSource(
    private val rickAndMortyApi: RickAndMortyApi
) {
    fun getCharacters(): LiveData<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CharacterPagingSource(rickAndMortyApi)
            }
        ).liveData
    }
}