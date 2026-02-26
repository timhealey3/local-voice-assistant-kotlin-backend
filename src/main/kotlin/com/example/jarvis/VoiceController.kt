package com.example.jarvis
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import kotlinx.serialization.json.Json

@RestController
class VoiceController(
    private val voiceService: VoiceService,
    private val voiceUtil: VoiceUtil,
    private val llmAPI: LLMAPI
) {
    private val folder_path = "../voiceOutput/"

    fun playSpotify(spotifyData: SpotifyData?): String {
        println("playing spotify")
        return "Currently playing ${spotifyData?.songName}"
    }

    fun callLargeModel(prompt: String): String {
        println("call largeModel()")
        val llmResponse = llmAPI.queryLargerLLM(prompt)
        val toolCall = Json.decodeFromString<ToolCalls>(llmResponse!!.response)
        return toolCall.chatResponse!!.text
    }

    @GetMapping("/textToVoice")
    fun textToVoice(prompt: String): Boolean {
        // small routing ollama model
        val llmResponse = llmAPI.queryRoutingLLM(prompt)
        val toolCall = Json.decodeFromString<ToolCalls>(llmResponse!!.response)
        val textOutput = when (toolCall.toolName) {
            "playSpotifySong" -> playSpotify(toolCall?.spotifyData)
            "Unsure" -> callLargeModel(prompt)
            else -> "I'm sorry I did not hear that, can you please repeat yourself?"
        }
        val outputFile: String = voiceService.postVoice(textOutput)
        voiceUtil.playMp3(folder_path + outputFile)
        return true
    }
}