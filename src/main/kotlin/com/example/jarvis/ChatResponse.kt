package com.example.jarvis
import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    val text: String,
)
