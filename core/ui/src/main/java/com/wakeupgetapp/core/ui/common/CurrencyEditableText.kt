package com.wakeupgetapp.core.ui.common

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun CurrencyEditableText(
    modifier: Modifier = Modifier,
    text: String = "",
    hint: String = "",
    textColor: Color = Color.White,
    hintColor: Color = Color.Gray,
    colorBrush: SolidColor = SolidColor(Color.LightGray),
    fontSize: TextUnit = 30.sp,
    textAlign: TextAlign = TextAlign.Start,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (String) -> Unit,
) {
    BasicTextField(
        onValueChange = onValueChange,
        value = text,
        textStyle = TextStyle.Default.copy(
            fontSize = fontSize,
            color = textColor,
            textAlign = textAlign
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        modifier = modifier,
        cursorBrush = colorBrush,
        decorationBox = { innerTextField ->

            if (text.isEmpty()) {
                Text(
                    modifier = modifier,
                    text = hint,
                    fontSize = fontSize,
                    color = hintColor,
                    textAlign = textAlign
                )
            }

            innerTextField()

        }
    )
}