package com.wakeupgetapp.feature.calculator.components.container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.wakeupgetapp.core.ui.common.CommonWhiteButton
import com.wakeupgetapp.core.ui.common.CurrencyBaseText
import com.wakeupgetapp.core.ui.common.CurrencyTextContainer
import com.wakeupgetapp.core.ui.common.anim.ShakeAnimation

@Composable
fun CurrencyRow(
    modifier: Modifier = Modifier,
    currencyName: String,
    currencyHint: String,
    amount: String,
    animateContainer: Boolean,
    currencyNameOnClick: () -> Unit,
    currencySelectorTextWidth: Dp,
    amountHint: String
) {
    Row(
        modifier
            .fillMaxWidth()
            .wrapContentSize(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ShakeAnimation(enable = animateContainer && currencyName.isBlank()) {
            CommonWhiteButton(
                text = currencyName,
                hint = currencyHint,
                icon = Icons.Default.KeyboardArrowDown,
                modifier = Modifier.width(currencySelectorTextWidth),
                onClick = { currencyNameOnClick() }
            )
        }

        CurrencyTextContainer(
            borderColor = Color.LightGray
        ) {
            CurrencyBaseText(
                modifier = Modifier.fillMaxWidth(),
                hint = amountHint,
                text = amount,
                textAlign = TextAlign.Center
            )
        }
    }
}