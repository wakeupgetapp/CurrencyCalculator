package com.wakeupgetapp.core.ui.common.anim

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun ShakeAnimation(
    enable: Boolean,
    startAnimOffset: Float = -10f,
    endAnimOffset: Float = 10f,
    durationMillis: Int = 100,
    animatedComponent: @Composable () -> Unit
) {
    val xOffset = if (!enable) 0f else {
        rememberInfiniteTransition("").animateFloat(
            initialValue = startAnimOffset,
            targetValue = endAnimOffset,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillis,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        ).value
    }

    Box(
        modifier = Modifier
            .graphicsLayer(translationX = xOffset)
    ) {
        animatedComponent()
    }
}