package com.example.rickandmorty


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class lazyKarakterler(
    @SerialName("info")
    val info: İnfoX,
    @SerialName("results")
    val results: List<ResultX>
)