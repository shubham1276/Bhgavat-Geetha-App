package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.example.model.ThemeMode

private val DarkColorScheme = darkColorScheme(
    primary = SaffronPrimaryDark,
    onPrimary = DarkSacredBg,
    primaryContainer = SaffronContainerDark,
    onPrimaryContainer = GoldAccentDark,
    secondary = GoldAccentDark,
    onSecondary = DarkSacredBg,
    secondaryContainer = GoldContainerDark,
    onSecondaryContainer = GoldAccentDark,
    tertiary = CelestialBlueDark,
    background = DarkSacredBg,
    onBackground = TextPrimaryDark,
    surface = DarkSacredSurface,
    onSurface = TextPrimaryDark,
    surfaceVariant = DarkSacredCard,
    onSurfaceVariant = TextSecondaryDark
)

private val LightColorScheme = lightColorScheme(
    primary = SaffronPrimary,
    onPrimary = SacredParchmentSurface,
    primaryContainer = SaffronContainerLight,
    onPrimaryContainer = SaffronPrimary,
    secondary = SaffronSecondary,
    onSecondary = SacredParchmentSurface,
    secondaryContainer = GoldContainerLight,
    onSecondaryContainer = SaffronSecondary,
    tertiary = DeepNavy,
    background = SacredParchmentBg,
    onBackground = TextPrimaryLight,
    surface = SacredParchmentSurface,
    onSurface = TextPrimaryLight,
    surfaceVariant = SacredParchmentCard,
    onSurfaceVariant = TextSecondaryLight
)

@Composable
fun BhagavadGeethaTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val isDark = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val colorScheme = if (isDark) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
