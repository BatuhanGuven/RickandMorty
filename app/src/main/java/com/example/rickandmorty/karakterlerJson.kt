package com.example.rickandmorty


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class karakterlerJson(
    @SerialName("info")
    val info: Info,
    @SerialName("results")
    val results: List<Result>
)