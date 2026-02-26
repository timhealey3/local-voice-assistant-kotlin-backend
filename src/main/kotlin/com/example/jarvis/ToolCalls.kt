package com.example.jarvis
import kotlinx.serialization.Serializable

@Serializable
data class ToolCalls(
    val toolName: String,
    val spotifyData: SpotifyData? = null,
    val chatResponse: ChatResponse? = null
)
