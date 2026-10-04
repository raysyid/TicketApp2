package com.example.ticketapp2.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val TiketColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = CardWhite,
    secondary = AccentPeach,
    onSecondary = CardWhite,
    background = AppBackground,
    onBackground = TextDark,
    surface = CardWhite,
    onSurface = TextDark,
    error = ErrorRed,
    onError = CardWhite
)

@Composable
fun TicketApp2Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = TiketColorScheme,
        typography = Typography,
        content = content
    )
}