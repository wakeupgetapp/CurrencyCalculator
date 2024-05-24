package com.wakeupgetapp.currencycalculator.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val ColorScheme = lightColorScheme(
    primary = Black,
    secondary = GreyBackground,
    tertiary = Pink80,
    background = GreyBackground,
    onBackground = LightGreyBackground
)

@Composable
fun CurrencyCalculatorTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content
    )
}