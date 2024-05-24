package com.wakeupgetapp.core.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CurrencyTextContainer(
    modifier: Modifier = Modifier,
    borderColor: Color = Color.White,
    backGroundColor: Color = Color.Unspecified,
    horizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.Center,
    onClick: () -> Unit,
    content: @Composable () -> Unit = {},
) {
    Row(
        modifier = modifier
            .padding(4.dp)
            .clip(shape = ShapeDefaults.Medium)
            .border(width = 2.dp, color = borderColor, shape = ShapeDefaults.Medium)
            .background(color = backGroundColor)
            .clickable { onClick() }
            .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement
    ) {
        content()
    }
}


@Composable
fun CurrencyTextContainer(
    modifier: Modifier = Modifier,
    borderColor: Color = Color.White,
    horizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.Center,
    content: @Composable () -> Unit = {},
) {
    Row(
        modifier = modifier
            .padding(4.dp)
            .clip(shape = ShapeDefaults.Medium)
            .border(width = 2.dp, color = borderColor, shape = ShapeDefaults.Medium)
            .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement
    ) {
        content()
    }
}