package com.wakeupgetapp.feature.calculator

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wakeupgetapp.core.model.CurrencyUpdateState
import com.wakeupgetapp.core.ui.common.anim.LoadingDotsAnimation
import com.wakeupgetapp.core.ui.common.dialog.InfoDialog
import com.wakeupgetapp.feature.calculator.components.updateCurrencyInfoBar.DataStateTextRow

@Composable
fun PreparingDataScreen(uiState: UiState, fetchCurrencies: () -> Unit) {
    var bottomInfo: @Composable () -> Unit = remember { {} }
    val showDialog = remember { mutableStateOf(false) }
    var dialogInfoText by remember {
        mutableStateOf("")
    }

    Log.d("UI", uiState.toString())

    when (uiState.dataState) {
        is DataState.Loaded -> {} /* no-op */


        is DataState.Loading -> {
            bottomInfo = {
                DataStateTextRow(textComponent = {
                    Text(
                        text = stringResource(id = R.string.loading_data),
                        color = Color.White
                    )
                },
                    trailingComponent = { LoadingDotsAnimation() })
            }
        }

        is DataState.Empty -> {
            when (uiState.currencyUpdateState) {
                CurrencyUpdateState.Loading -> {
                    bottomInfo = {
                        DataStateTextRow(textComponent = {
                            Text(
                                text = stringResource(id = R.string.downloading_data),
                                color = Color.White
                            )
                        },
                            trailingComponent = { LoadingDotsAnimation() })
                    }
                }

                CurrencyUpdateState.CurrencyUpdateFailure -> {
                    bottomInfo = { }
                    dialogInfoText = stringResource(id = R.string.init_network_error)
                    showDialog.value = true
                }

                is CurrencyUpdateState.Failure -> {
                    bottomInfo = { }
                    dialogInfoText = stringResource(id = R.string.init_api_error)
                    showDialog.value = true
                }

                CurrencyUpdateState.Success -> {
                    bottomInfo = {
                        DataStateTextRow(textComponent = {
                            Text(
                                text = stringResource(id = R.string.downloaded_data),
                                color = Color.White
                            )
                        },
                            trailingComponent = {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = null,
                                    tint = Color.Green
                                )
                            })
                    }
                }
            }
        }
    }

    InfoDialog(
        showDialog = showDialog,
        mainText = dialogInfoText,
        buttonText = stringResource(id = R.string.try_again),
        buttonIcon = Icons.Default.Refresh,
        onClick = { fetchCurrencies() })

    Column(
        Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.BottomCenter)
            .padding(bottom = 16.dp)
    ) {
        bottomInfo()
    }

}
