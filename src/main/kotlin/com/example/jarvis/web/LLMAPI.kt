package com.example.jarvis.web

import io.github.ollama4j.OllamaAPI
import io.github.ollama4j.models.response.OllamaResult
import org.springframework.stereotype.Component

@Component
class LLMAPI {
    private val routingPrompt = """
    You are a routing engine. Your output must be RAW JSON ONLY. Do not include markdown, backticks, or explanation.
    
    ### TOOLS
    1. playSpotifySong: Use this if the user wants to hear music.
    2. turnOnLight: Use this for smart home lighting requests.
    3. unsure: Use this if the request is a general question, chat, or if you aren't 100% certain.

    ### OUTPUT SCHEMA
    You must return a JSON object with exactly this structure:
    {
      "toolName": "unsure" | "spotify" | "light",
      "unsureData": { "textOutput": "reasoning here" },
      "spotifyData": { "songName": "name here" }
    }

    ### RULES
    - If the users request is unclear or not something the tools can handle, always pick "unsure".
    - If toolName is "unsure", include "unsureData" and do NOT add "spotifyData".
    - If toolName is "spotify", include "spotifyData" and do NOT add "unsureData".
    - Use LOWERCASE for toolNames to match the Kotlin @SerialName configuration.
""".trimIndent()

    private val generatingPrompt = """
    You are a helpful voice assistant. Your output must be a single RAW JSON object. 
    Do not use markdown formatting, backticks, or special characters like *, #, or _.
    
    ### JSON STRUCTURE
    {
      "toolName": "chatResponse",
      "chatResponse": {
        "text": "Your spoken response here"
      }
    }

    ### RULES
    1. Output ONLY in the JSON structure of 
    {
      "toolName": "chatResponse",
      "chatResponse": {
        "text": "Your spoken response here"
      }
    } .
    2. The "text" field must be plain text optimized for Text-to-Speech (no symbols).
    3. Ensure "toolName" is exactly "chatResponse" so the system can route it.

    Answer this question: 
""".trimIndent()

    fun queryRoutingLLM(prompt: String): OllamaResult? {
        val host = "http://localhost:11434/"
        val ollamaAPI = OllamaAPI(host)
        ollamaAPI.setRequestTimeoutSeconds(120)
        val response: OllamaResult? = ollamaAPI.generate(
            "qwen3:0.6b",
            (routingPrompt + prompt),
            null
        )
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
        return response
    }
}