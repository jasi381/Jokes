package com.example.jokes

data class Joke(
    val category: String?,
    val delivery: String?,
    val flags: Flags?,
    val id: Int?,
    val joke: String?,
    val lang: String?,
    val safe: Boolean?,
    val setup: String?,
    val type: String?
)