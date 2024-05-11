package com.example.jokes

data class ApiResponse(
    val amount: Int?,
    val error: Boolean?,
    val jokes: List<Joke>
)