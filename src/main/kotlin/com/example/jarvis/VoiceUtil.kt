package com.example.jarvis

import java.io.FileInputStream
import java.io.IOException
import javazoom.jl.player.Player
import org.springframework.stereotype.Component

@Component
class VoiceUtil {
    fun playMp3(path: String) {
        try {
            FileInputStream(path).use { fis ->
                val player = Player(fis)
                player.play()
            }
        } catch(e: IOException) {
            println("error while playing $path")
        }
    }
}