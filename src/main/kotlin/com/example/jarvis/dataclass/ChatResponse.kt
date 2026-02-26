package com.example.jarvis.dataclass
import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    val text: String,
)