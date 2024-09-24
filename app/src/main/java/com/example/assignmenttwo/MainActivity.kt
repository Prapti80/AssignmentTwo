package com.example.assignmenttwo


import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.assignmenttwo.ui.theme.AssignmentTwoTheme

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize media player
        mediaPlayer = MediaPlayer.create(this, R.raw.sample_music)  // Replace with your actual file in res/raw

        setContent {
            AssignmentTwoTheme {
                MainFeature(
                    onMusicPlay = { playMusic() },
                    onMusicSeek = { position -> seekMusic(position) },
                    onMusicStop = { stopMusic() },
                    getMusicDuration = { mediaPlayer.duration }
                )
            }
        }
    }

    // Function to start playing the music
    private fun playMusic() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    // Function to stop/pause the music
    private fun stopMusic() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    // Function to seek the music to a specific position
    private fun seekMusic(position: Int) {
        if (position in 0..mediaPlayer.duration) {
            mediaPlayer.seekTo(position)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()  // Release resources when activity is destroyed
    }
}
