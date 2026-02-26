package com.example.jarvis.util

import org.springframework.stereotype.Component
import java.io.FileInputStream
import java.io.IOException
import javazoom.jl.player.Player

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