package com.wakeupgetapp.feature.selector.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wakeupgetapp.core.ui.common.CurrencyBaseText

@Composable
fun EmptyTab(text: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
            .padding(16.dp)
    ) {
        CurrencyBaseText(
            text = text,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
        )
    }
}

@Composable
fun SingleTab(selectedTabIndex: MutableIntState, text: String, index: Int) {
    Tab(
        selected = selectedTabIndex.intValue == index,
        onClick = { selectedTabIndex.intValue = index }
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(top = 12.dp, bottom = 12.dp),
        )
    }
}
