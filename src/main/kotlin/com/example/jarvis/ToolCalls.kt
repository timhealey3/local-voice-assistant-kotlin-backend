package com.example.jarvis
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
@JsonClassDiscriminator("toolName")
sealed class ToolCalls {
    abstract val toolName: String

    @Serializable
    @SerialName("unsure")
    data class Unsure(
        override val toolName: String,
        val unsureData: UnsureData
    ) : ToolCalls()

    @Serializable
    @SerialName("spotifyData")
    data class Spotify(
        override val toolName: String,
        val spotifyData: SpotifyData
    ) : ToolCalls()

    @Serializable
    @SerialName("chatResponse")
    data class Chat(
        override val toolName: String,
        val chatResponse: ChatResponse
    ) : ToolCalls()
}
