package com.wakeupgetapp.core.ui.common

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.wakeupgetapp.core.ui.R

@Composable
fun CommonDarkButton(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 30.sp,
    icon: ImageVector? = null,
    onClick: () -> Unit
) {
    CurrencyTextContainer(
        borderColor = Color.Black,
        backGroundColor = Color.DarkGray,
        onClick = { onClick() }
    ) {
        CurrencyBaseText(
            modifier = modifier,
            text = text,
            fontSize = fontSize,
            textAlign = TextAlign.Center,
            color = Color.White
        )

        if (icon != null) Icon(
            imageVector = icon,
            contentDescription = stringResource(id = R.string.add),
            tint = Color.White
        )

    }
}