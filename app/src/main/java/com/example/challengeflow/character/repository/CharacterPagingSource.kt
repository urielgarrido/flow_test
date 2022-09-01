package com.example.challengeflow.character.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.challengeflow.character.model.Character
import com.example.challengeflow.character.model.rest.toCharacter
import com.example.challengeflow.retrofit.RickAndMortyApi
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1
private const val NETWORK_PAGE_SIZE = 42

class CharacterPagingSource(
    private val rickAndMortyApi: RickAndMortyApi
) : PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = rickAndMortyApi.getCharacters(
                page = pageIndex
            )
            val characters = response.body()?.results?.map { it.toCharacter() }
            val nextKey =
                if (characters?.isEmpty() == true) {
                    null
                } else {
                    pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
                }
            LoadResult.Page(
                data = characters ?: emptyList(),
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }

    }


}