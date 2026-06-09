package com.example.studyplan.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Blue40,
    onPrimary = Neutral98,
    primaryContainer = Blue90,
    onPrimaryContainer = Blue10,

    // Secondary and tertiary share the blue-grey family to keep the palette quiet.
    secondary = BlueGrey40,
    onSecondary = Neutral98,
    secondaryContainer = BlueGrey90,
    onSecondaryContainer = BlueGrey10,

    tertiary = BlueGrey40,
    onTertiary = Neutral98,
    tertiaryContainer = BlueGrey90,
    onTertiaryContainer = BlueGrey10,

    background = Neutral98,
    onBackground = Neutral10,
    surface = Neutral98,
    onSurface = Neutral10,
    surfaceVariant = NeutralVariant90,
    onSurfaceVariant = NeutralVariant30,
    outline = NeutralVariant50,
    outlineVariant = NeutralVariant80,
)

private val DarkColorScheme = darkColorScheme(
    primary = Blue80,
    onPrimary = Blue20,
    primaryContainer = Blue30,
    onPrimaryContainer = Blue90,

    secondary = BlueGrey80,
    onSecondary = BlueGrey20,
    secondaryContainer = BlueGrey30,
    onSecondaryContainer = BlueGrey90,

    tertiary = BlueGrey80,
    onTertiary = BlueGrey20,
    tertiaryContainer = BlueGrey30,
    onTertiaryContainer = BlueGrey90,

    background = Neutral10,
    onBackground = Neutral90,
    surface = Neutral10,
    onSurface = Neutral90,
    surfaceVariant = NeutralVariant30,
    onSurfaceVariant = NeutralVariant80,
    outline = NeutralVariant60,
    outlineVariant = NeutralVariant30,
)

@Composable
fun StudyPlanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
