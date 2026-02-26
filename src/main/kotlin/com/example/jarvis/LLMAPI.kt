package com.example.jarvis

import io.github.ollama4j.OllamaAPI
import io.github.ollama4j.models.response.OllamaResult
import org.springframework.stereotype.Component

@Component
class LLMAPI {
    private val routingPrompt = "You are a routing LLM. Only route to a tool call if you are confident you are correct, if you are not confident pick the tool Unsure" +
            " the tools you can use are 1. playSpotifySong 2. turnOnLight 3. Unsure. Return ONLY the toolName in JSON {toolName: } if it is a Spotify song return add {spotifyData: {songName:}} to your JSON response "

    private val generatingPrompt = "You are a helpful voice assistant like Amazon Alexa, don't answer with any special characters as this will be run though a text to speech program" +
            " Return ONLY the toolName in JSON {toolName: } as Answer and add {chatResponse: {text:}} to your JSON response with text being the output response. please answer this question: "

    fun queryRoutingLLM(prompt: String): OllamaResult? {
        val host = "http://localhost:11434/"
        val ollamaAPI = OllamaAPI(host)
        ollamaAPI.setRequestTimeoutSeconds(120)
        val response: OllamaResult? = ollamaAPI.generate(
            "qwen3:0.6b",
            (routingPrompt + prompt),
            null
        )
        println(response)
        return response
    }

    fun queryLargerLLM(prompt: String): OllamaResult? {
        val host = "http://localhost:11434/"
        val ollamaAPI = OllamaAPI(host)
        ollamaAPI.setRequestTimeoutSeconds(120)
        val response: OllamaResult? = ollamaAPI.generate(
            "qwen3:0.6b",
            (generatingPrompt + prompt),
            null
        )
        println(response)
        return response
    }
}