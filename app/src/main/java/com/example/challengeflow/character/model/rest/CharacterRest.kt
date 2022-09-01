package com.example.challengeflow.character.model.rest

import com.example.challengeflow.character.model.Character
import com.google.gson.annotations.SerializedName

data class CharacterRest(
    @SerializedName("info") val info: InfoCharacterRest,
    @SerializedName("results") val results: List<ResultCharacterRest>,
)

data class InfoCharacterRest(
    @SerializedName("count") val count: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("prev") val prev: String?,
)

data class ResultCharacterRest(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("image") val image: String,
)

fun ResultCharacterRest.toCharacter(): Character = Character(
    id, name, status, species, gender, image
)
