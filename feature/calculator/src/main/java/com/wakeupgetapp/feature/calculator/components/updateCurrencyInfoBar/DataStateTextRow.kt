package com.wakeupgetapp.feature.calculator.components.updateCurrencyInfoBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DataStateTextRow(
    textComponent: @Composable () -> Unit,
    trailingComponent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        textComponent()
        trailingComponent()
    }
}