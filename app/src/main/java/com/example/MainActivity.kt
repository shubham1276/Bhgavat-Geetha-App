package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.GitaViewModel
import com.example.ui.MainScreen
import com.example.ui.theme.BhagavadGeethaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val extraVerseId = intent?.getStringExtra("EXTRA_VERSE_ID")

        setContent {
            val viewModel: GitaViewModel = viewModel()
            val settings by viewModel.settings.collectAsState()

            // Handle direct deep link or notification navigation
            if (!extraVerseId.isNullOrBlank()) {
                viewModel.openVerseDetail(extraVerseId)
            }

            BhagavadGeethaTheme(themeMode = settings.themeMode) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}
