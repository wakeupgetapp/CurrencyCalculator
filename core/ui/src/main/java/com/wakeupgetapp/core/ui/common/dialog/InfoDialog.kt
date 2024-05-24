package com.wakeupgetapp.core.ui.common.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.wakeupgetapp.core.ui.common.CommonDarkButton
import com.wakeupgetapp.core.ui.common.CommonWhiteButton
import com.wakeupgetapp.core.ui.common.CurrencyBaseText

@Composable
fun InfoDialog(
    showDialog: MutableState<Boolean>,
    mainText: String,
    buttonText: String,
    buttonIcon: ImageVector,
    dismissOnClickOutside: Boolean = false,
    dismissOnBackPress: Boolean = false,
    onClick: () -> Unit
) {
    if (showDialog.value) {
        Dialog(
            onDismissRequest = {
                showDialog.value = false
                onClick()
            },
            properties = DialogProperties(
                dismissOnClickOutside = dismissOnClickOutside,
                dismissOnBackPress = dismissOnBackPress
            )
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 24.dp,
                        bottom = 16.dp
                    ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CurrencyBaseText(
                        text = mainText,
                        color = Color.Black,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            lineBreak = LineBreak.Heading
                        )
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    CommonDarkButton(
                        text = buttonText,
                        fontSize = 24.sp,
                        icon = buttonIcon,
                    ) {
                        showDialog.value = false
                        onClick()
                    }
                }
            }
        }
    }
}

@Composable
fun InfoDialog(
    showDialog: MutableState<Boolean>,
    mainText: String,
    approveButtonText: String,
    declineButtonText: String,
    dismissOnClickOutside: Boolean = true,
    dismissOnBackPress: Boolean = true,
    onApproveClick: () -> Unit,
    onDeclineClick: () -> Unit
) {
    if (showDialog.value) {
        Dialog(
            onDismissRequest = {
                showDialog.value = false
            },
            properties = DialogProperties(
                dismissOnClickOutside = dismissOnClickOutside,
                dismissOnBackPress = dismissOnBackPress
            )
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 24.dp,
                        bottom = 16.dp
                    ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CurrencyBaseText(
                        text = mainText,
                        color = Color.Black,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            lineBreak = LineBreak.Heading
                        )
                    )
                    Spacer(modifier = Modifier.padding(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CommonWhiteButton(
                            modifier = Modifier.defaultMinSize(minWidth = 52.dp),
                            text = approveButtonText,
                            textColor = Color.Black,
                            fontSize = 24.sp,
                            onClick = {
                                showDialog.value = false
                                onDeclineClick()
                            }
                        )
                        CommonDarkButton(
                            modifier = Modifier.defaultMinSize(minWidth = 52.dp),
                            text = declineButtonText,
                            fontSize = 24.sp,
                            onClick = {
                                showDialog.value = false
                                onApproveClick()
                            }
                        )
                    }
                }
            }
        }
    }
}