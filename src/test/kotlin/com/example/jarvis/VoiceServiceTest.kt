package com.example.jarvis

import com.example.jarvis.util.VoiceUtil
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.web.reactive.function.client.WebClient

class VoiceServiceTest {

    private val voiceService = VoiceUtil()

    @Test
    fun `playMp3 should not throw exception when called`() {
        // We can't easily test actual audio output in a unit test,
        // but we can ensure the function runs without crashing for a non-existent file
        // or we could mock ProcessBuilder if we wanted to be more thorough.
        // For now, just verifying it exists and can be called.
        voiceService.playMp3("non_existent.mp3")
    }
}
