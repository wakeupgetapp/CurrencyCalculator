package com.wakeupgetapp.core.ui.common.util

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun calculateSystemBottomPadding(): State<Dp> {
    val bottomHeightDp = remember { mutableStateOf(0.dp) }
    var bottomHeight by remember {
        mutableIntStateOf(0)
    }
    val view = LocalView.current
    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            bottomHeight = screenHeight - rect.bottom

        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)
        onDispose { }
    }
    bottomHeightDp.value = bottomHeight.pxToDp()
    return bottomHeightDp
}