package com.wakeupgetapp.feature.calculator.components.updateCurrencyInfoBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.wakeupgetapp.core.model.CurrencyUpdateState
import com.wakeupgetapp.core.ui.common.anim.LoadingDotsAnimation
import com.wakeupgetapp.core.ui.common.dialog.InfoDialog
import com.wakeupgetapp.feature.calculator.R


@Composable
fun RatesInfoComponent(
    currencyUpdateState: CurrencyUpdateState,
    tryAgainButton: () -> Unit
) {
    var textComponent: @Composable () -> Unit = remember { {} }
    var trailingComponent: @Composable () -> Unit = remember { {} }
    val showInfoDialog = remember { mutableStateOf(false) }

    InfoDialog(showDialog = showInfoDialog,
        mainText = stringResource(id = R.string.default_network_failure_dialog),
        approveButtonText = stringResource(id = R.string.back),
        declineButtonText = stringResource(id = R.string.try_again),
        onApproveClick = tryAgainButton,
        onDeclineClick = { showInfoDialog.value = false })

    when (currencyUpdateState) {
        CurrencyUpdateState.Loading -> {
            textComponent = { Text(text = stringResource(id = R.string.updating_data), color = Color.White) }
            trailingComponent = { LoadingDotsAnimation() }
        }

        is CurrencyUpdateState.Failure, CurrencyUpdateState.CurrencyUpdateFailure -> {
            textComponent = {
                Row(modifier = Modifier.clickable {
                    showInfoDialog.value = true
                }) {
                    Text(
                        text = "${stringResource(id = R.string.failed_to_update_data)} ",
                        color = Color.White
                    )
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            trailingComponent = {
                Text(text = ". ", color = Color.White)
                Text(
                    text = stringResource(id = R.string.try_again),
                    modifier = Modifier.clickable { tryAgainButton() },
                    color = Color.Cyan
                )
            }
        }

        CurrencyUpdateState.Success -> {
            textComponent = {
                Text(
                    text = stringResource(id = R.string.data_is_up_to_date),
                    color = Color.White
                )
            }
            trailingComponent = {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = Color.Green
                )
            }
        }
    }

    DataStateTextRow(textComponent = textComponent, trailingComponent = trailingComponent)
}