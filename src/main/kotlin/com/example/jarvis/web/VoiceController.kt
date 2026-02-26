package com.example.jarvis.web
import com.example.jarvis.dataclass.SpotifyData
import com.example.jarvis.dataclass.ToolCalls
import com.example.jarvis.util.VoiceUtil
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

    fun playSpotify(spotifyData: SpotifyData): String {
        println("playing spotify")
        return "Currently playing ${spotifyData.songName}"
    }

    fun callLargeModel(prompt: String): String {
        println("call largeModel()")
        val llmResponse = llmAPI.queryLargerLLM(prompt)
        val toolCall: ToolCalls = Json.decodeFromString<ToolCalls>(llmResponse!!.response)
        return if (toolCall is ToolCalls.Chat) {
            toolCall.chatResponse.text
        }else{
            "unkown"
        }
    }

    @GetMapping("/textToVoice")
    fun textToVoice(prompt: String): Boolean {
        // small routing ollama model
        val llmResponse = llmAPI.queryRoutingLLM(prompt)
        val toolCall = Json.decodeFromString<ToolCalls>(llmResponse!!.response)
        val textOutput = when (toolCall) {
            is ToolCalls.Spotify-> playSpotify(toolCall.spotifyData)
            is ToolCalls.Unsure -> callLargeModel(prompt)
            is ToolCalls.Chat -> toolCall.chatResponse.text
            else -> "Can you please repeat that?"
        }
        val outputFile: String = voiceService.postVoice(textOutput)
        voiceUtil.playMp3(folder_path + outputFile)
        return true
    }
}