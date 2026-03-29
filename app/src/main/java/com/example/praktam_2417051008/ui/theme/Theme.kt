package com.example.praktam_2417051008.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = NavyQuiz,
    onPrimary = Color.White,
    secondary = DeepBlueQuiz,
    onSecondary = Color.White,
    primaryContainer = BabyBlueQuiz,
    onPrimaryContainer = DeepBlueQuiz,
    surface = Color.White,
    onSurface = NavyQuiz,
    secondaryContainer = SuccessGreen,
    error = ErrorRed,
    outline = Penjelasan
)

@Composable
fun PrakTAM_2417051008Theme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}