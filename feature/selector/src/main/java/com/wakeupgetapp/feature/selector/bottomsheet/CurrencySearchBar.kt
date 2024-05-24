package com.wakeupgetapp.feature.selector.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wakeupgetapp.core.ui.common.CurrencyEditableText
import com.wakeupgetapp.core.ui.common.CurrencyTextContainer
import com.wakeupgetapp.feature.selector.R


@Composable
fun CurrencySearchBar(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit
) {
    CurrencyTextContainer(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        borderColor = Color.Gray
    ) {
        Box {
            CurrencyEditableText(
                text = text,
                fontSize = 20.sp,
                hint = stringResource(id = R.string.search),
                modifier = modifier.fillMaxWidth(),
                textColor = Color.DarkGray,
                hintColor = Color.LightGray,
                colorBrush = SolidColor(Color.Gray),
                onValueChange = onValueChange
            )
            Icon(
                modifier = Modifier.align(Alignment.CenterEnd),
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search_bar),
                tint = Color.Gray
            )
        }
    }
}