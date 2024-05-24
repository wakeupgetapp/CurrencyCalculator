package com.wakeupgetapp.feature.selector.bottomsheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wakeupgetapp.core.model.CustomExchange
import com.wakeupgetapp.core.ui.common.dialog.InfoDialog
import com.wakeupgetapp.core.ui.common.util.clickableNoRipple
import com.wakeupgetapp.feature.selector.R
import com.wakeupgetapp.feature.selector.customExchangeDialog.CustomExchangeDialog

@Composable
fun CustomExchangeButton(
    customExchange: CustomExchange,
    onClick: (Long) -> Unit,
    editCurrencyExchange: (String, String, Double, Long) -> Unit,
    deleteCurrencyExchange: (Long) -> Unit,
) {

    var visible by remember { mutableStateOf(false) }
    val rotationValue = remember { Animatable(90f) }
    val showCustomExchangeDialogEdit = remember { mutableStateOf(false) }
    val showCustomExchangeDialogDelete = remember { mutableStateOf(false) }
    val targetRotation = if (visible) -90f else 90f

    LaunchedEffect(visible) {
        rotationValue.animateTo(
            targetValue = targetRotation, animationSpec = tween(durationMillis = 500)
        )
    }

    CustomExchangeDialog(
        showDialog = showCustomExchangeDialogEdit,
        onDoneAction = { baseCode: String, targetCode: String, rate: Double ->
            editCurrencyExchange(baseCode, targetCode, rate, customExchange.id)
        },
        baseCode = customExchange.base,
        targetCode = customExchange.target,
        rate = customExchange.rate.toBigDecimal().toPlainString().trimEnd('0').trimEnd('.')
    )

    InfoDialog(showDialog = showCustomExchangeDialogDelete,
        mainText = "${stringResource(id = R.string.are_you_sure_delete)} " +
                "${customExchange.base} - ${customExchange.target}?",
        approveButtonText = stringResource(id = R.string.no),
        declineButtonText = stringResource(id = R.string.yes),
        onApproveClick = { deleteCurrencyExchange(customExchange.id) },
        onDeclineClick = { showCustomExchangeDialogDelete.value = false })

    Row {
        Row(
            modifier = Modifier
                .clip(shape = ShapeDefaults.Small)
                .clickable { onClick(customExchange.id) }
                .padding(top = 12.dp, bottom = 12.dp, start = 4.dp, end = 4.dp)
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = customExchange.base,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "-",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = customExchange.target,
                modifier = Modifier.weight(1f),
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )

        }

        Icon(imageVector = Icons.Default.ArrowDropDown,
            contentDescription = stringResource(id = R.string.expand),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clip(ShapeDefaults.Small)
                .rotate(rotationValue.value)
                .clickableNoRipple { visible = !visible }
                .padding(top = 12.dp, bottom = 12.dp, start = 12.dp, end = 12.dp))

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.CenterVertically),
            visible = visible,
            enter = expandHorizontally(),
            exit = shrinkHorizontally()
        ) {
            Row(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Icon(imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(id = R.string.edit_custom_exchange),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clip(ShapeDefaults.Small)
                        .clickable { showCustomExchangeDialogEdit.value = true }
                        .padding(top = 12.dp, bottom = 12.dp, start = 12.dp, end = 12.dp))

                Icon(imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(id = R.string.delete_custom_exchange),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clip(ShapeDefaults.Small)
                        .clickable { showCustomExchangeDialogDelete.value = true }
                        .padding(top = 12.dp, bottom = 12.dp, start = 12.dp, end = 12.dp)

                )
            }
        }
    }
    HorizontalDivider(modifier = Modifier.padding(start = 4.dp, end = 20.dp))
}