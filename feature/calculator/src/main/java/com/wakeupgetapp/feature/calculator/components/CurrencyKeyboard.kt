package com.wakeupgetapp.feature.calculator.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CurrencyKeyboard(
    modifier: Modifier = Modifier,
    onClick: (String, Boolean) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.DarkGray, Color.Gray)
                ),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            )
            .padding(top = 16.dp, start = 16.dp, bottom = 16.dp, end = 16.dp)

    ) {
        CurrencyRow("1", "2", "3", onClick)
        CurrencyRow("4", "5", "6", onClick)
        CurrencyRow("7", "8", "9", onClick)
        CurrencyRow(".", "0", "<", onClick)
    }
}

@Composable
fun CurrencyRow(
    firstText: String,
    secondText: String,
    thirdText: String,
    onClick: (String, Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
    ) {
        CurrencyButton(onClick = onClick, text = firstText)
        CurrencyButton(onClick = onClick, text = secondText)
        CurrencyButton(onClick = onClick, text = thirdText)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowScope.CurrencyButton(
    onClick: (String, Boolean) -> Unit,
    text: String,
    fontSize: TextUnit = 46.sp
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(32.dp))
            .combinedClickable(
                onClick = { onClick(text, false) },
                onLongClick = { onClick(text, true) }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, fontSize = fontSize, color = Color.White, modifier = Modifier.padding(8.dp))
    }
}