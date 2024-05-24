package com.wakeupgetapp.feature.selector.customExchangeDialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wakeupgetapp.core.ui.common.CurrencyBaseText
import com.wakeupgetapp.core.ui.common.CurrencyEditableText
import com.wakeupgetapp.core.ui.common.CurrencyTextContainer
import com.wakeupgetapp.core.ui.common.util.PlaceComponentWithMeasuredWidth
import com.wakeupgetapp.core.ui.common.util.WidestCurrencyCodeText
import com.wakeupgetapp.feature.selector.R

@Composable
fun CurrencyNameColumn(
    firstCurrencyName: MutableState<String>, secondCurrencyName: MutableState<String>
) {
    Column(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
        NameCurrencyRow(rowName = stringResource(id = R.string.dialog_base),
            currencyName = firstCurrencyName.value,
            hint = "USD",
            onValueChange = {
                if (it.length < 4) firstCurrencyName.value = it.uppercase()
            })

        Spacer(modifier = Modifier.size(16.dp))

        NameCurrencyRow(rowName = stringResource(id = R.string.dialog_target),
            currencyName = secondCurrencyName.value,
            hint = "EUR",
            onValueChange = {
                if (it.length < 4) secondCurrencyName.value = it.uppercase()
            })
    }

}

@Composable
fun NameCurrencyRow(rowName: String, currencyName: String, hint: String, onValueChange: (String) -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CurrencyBaseText(
            modifier = Modifier.weight(1f, fill = false),
            text = rowName,
            textAlign = TextAlign.Start,
            color = Color.Black
        )

        CurrencyTextContainer(
            borderColor = Color.DarkGray, modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.End)
        ) {
            PlaceComponentWithMeasuredWidth(viewToMeasure = {
                WidestCurrencyCodeText(fontSize = 30.sp)
            }) {
                CurrencyEditableText(
                    modifier = Modifier.width(it),
                    text = currencyName,
                    hint = hint,
                    textColor = Color.DarkGray,
                    hintColor = Color.Gray,
                    colorBrush = SolidColor(Color.DarkGray),
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    onValueChange = onValueChange
                )
            }
        }
    }
}
