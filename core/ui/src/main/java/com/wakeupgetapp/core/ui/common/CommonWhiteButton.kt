package com.wakeupgetapp.core.ui.common

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun CommonWhiteButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color.White,
    fontSize: TextUnit = 30.sp,
    hint: String = "",
    icon: ImageVector? = null,
    iconColor: Color = Color.White,
    onClick: ()-> Unit
) {
    CurrencyTextContainer(
        onClick = { onClick() },
        borderColor = Color.LightGray
    ) {
        CurrencyBaseText(
            text = text,
            hint = hint,
            textColor = textColor,
            hintColor = Color.Gray,
            fontSize = fontSize,
            textAlign = TextAlign.Center,
            modifier = modifier
        )
        if (icon != null)
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor
            )
    }


}