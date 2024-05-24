package com.wakeupgetapp.feature.selector.bottomsheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wakeupgetapp.core.model.Currency

@Composable
fun CurrencySelectorButton(currency: Currency, currencyCodeWidth: Dp, onClick: (String) -> Unit) {

    Column(modifier = Modifier.clip(shape = ShapeDefaults.Medium)) {
        Row(
            modifier = Modifier
                .clickable { onClick(currency.code) }
                .padding(top = 12.dp, bottom = 12.dp, start = 4.dp, end = 4.dp)
        ) {
            Text(
                text = currency.code,
                modifier = Modifier
                    .defaultMinSize(currencyCodeWidth)
                    .wrapContentWidth(),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
            )

            Text(
                text = "- ",
                modifier = Modifier,
                fontSize = 24.sp,
                textAlign = TextAlign.Start,
            )

            Text(
                text = currency.name,
                style = TextStyle(
                    lineBreak = LineBreak.Heading
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp),
                fontSize = 24.sp,
                textAlign = TextAlign.Start
            )
        }
        HorizontalDivider(modifier = Modifier.padding(start = 4.dp, end = 4.dp))
    }

}
