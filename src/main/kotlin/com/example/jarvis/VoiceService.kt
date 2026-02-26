package com.example.jarvis
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class VoiceService(private val webClient: WebClient) {

    fun postVoice(prompt: String): String {
        val url = "http://127.0.0.1:5000?llmResponseString=$prompt"
        return webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()!!
    }
}