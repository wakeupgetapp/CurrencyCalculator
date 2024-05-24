package com.wakeupgetapp.core.ui.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun CurrencyBaseText(
    modifier: Modifier = Modifier,
    text: String = "",
    fontSize: TextUnit = 30.sp,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle = TextStyle.Default,
    color: Color = Color.White
) {
    Text(
        text = text.ifBlank { " " },
        fontSize = fontSize,
        textAlign = textAlign,
        style = style,
        modifier = modifier,
        color = color
    )
}

@Composable
fun CurrencyBaseText(
    modifier: Modifier = Modifier,
    text: String = " ",
    hint: String,
    fontSize: TextUnit = 30.sp,
    textAlign: TextAlign = TextAlign.Start,
    textColor: Color = Color.White,
    hintColor: Color = Color.Gray
) {
    if (text.isNotBlank()) {
        Text(
            text = text,
            fontSize = fontSize,
            textAlign = textAlign,
            modifier = modifier,
            color = textColor
        )
    } else {
        Text(
            text = hint.ifBlank { " " },
            fontSize = fontSize,
            textAlign = textAlign,
            modifier = modifier,
            color = hintColor
        )
    }
}