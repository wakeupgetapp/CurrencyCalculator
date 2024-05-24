package com.wakeupgetapp.feature.selector.bottomsheet.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wakeupgetapp.core.model.CustomExchange
import com.wakeupgetapp.core.ui.common.CommonDarkButton
import com.wakeupgetapp.feature.selector.R
import com.wakeupgetapp.feature.selector.bottomsheet.CustomExchangeButton
import com.wakeupgetapp.feature.selector.bottomsheet.EmptyTab
import com.wakeupgetapp.feature.selector.customExchangeDialog.CustomExchangeDialog

@Composable
fun CustomExchangeTab(
    customExchanges: List<CustomExchange>,
    setCustomExchange: (Long) -> Unit,
    addCurrencyExchange: (String, String, Double) -> Unit,
    editCurrencyExchange: (String, String, Double, Long) -> Unit,
    deleteCurrencyExchange: (Long) -> Unit,
    bottomPadding: Dp
) {
    val showCustomExchangeDialogAdd = remember { mutableStateOf(false) }

    CustomExchangeDialog(
        showDialog = showCustomExchangeDialogAdd,
        onDoneAction = addCurrencyExchange
    )

    Scaffold(
        Modifier
            .padding(start = 16.dp)
            .background(Color.Transparent),
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(bottom = 0.dp)
        ,
        floatingActionButton = {
            Column {
                CommonDarkButton(
                    modifier = Modifier
                        .wrapContentSize(),
                    text = stringResource(id = R.string.add),
                    icon = Icons.Default.Add,
                    onClick = { showCustomExchangeDialogAdd.value = true }
                )
                Spacer(modifier = Modifier.size(bottomPadding))
            }

        }) { padding ->
        if (customExchanges.isEmpty()) {
            EmptyTab(text = stringResource(id = R.string.new_exchange_info))
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding),
            ) {
                items(items = customExchanges, key = { it.id }) {
                    CustomExchangeButton(
                        customExchange = it,
                        onClick = { id: Long ->
                            setCustomExchange(id)
                            editCurrencyExchange(it.base, it.target, it.rate, it.id)
                        },
                        editCurrencyExchange = editCurrencyExchange,
                        deleteCurrencyExchange = deleteCurrencyExchange
                    )
                }
                item {
                    Spacer(modifier = Modifier.size(bottomPadding))
                }
            }
        }
    }

}