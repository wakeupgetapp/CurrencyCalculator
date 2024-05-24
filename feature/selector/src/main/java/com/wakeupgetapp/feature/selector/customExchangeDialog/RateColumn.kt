package com.wakeupgetapp.feature.selector.customExchangeDialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wakeupgetapp.core.ui.common.CurrencyBaseText
import com.wakeupgetapp.core.ui.common.CurrencyEditableText
import com.wakeupgetapp.core.ui.common.CurrencyTextContainer

@Composable
fun RateColumn(
    firstCurrencyName: String,
    secondCurrencyName: String,
    amount: String,
    onDone: () -> Unit,
    onValueChange: (String) -> Unit
) {
    Column(Modifier.padding(top = 16.dp, bottom = 16.dp)) {
        CurrencyBaseText(
            text = "1 $firstCurrencyName =",
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth(),
            color = Color.Black
        )

        CurrencyTextContainer(
            borderColor = Color.DarkGray, modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            CurrencyEditableText(
                modifier = Modifier.weight(1f),
                text = amount,
                hint = "1.107",
                textColor = Color.DarkGray,
                colorBrush = SolidColor(Color.DarkGray),
                hintColor = Color.Gray,
                fontSize = 30.sp,
                textAlign = TextAlign.End,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { onDone() }),
                onValueChange = onValueChange
            )

            CurrencyBaseText(
                modifier = Modifier,
                text = " $secondCurrencyName",
                color = Color.DarkGray,
                fontSize = 30.sp,
                textAlign = TextAlign.End,
            )
        }
    }
}