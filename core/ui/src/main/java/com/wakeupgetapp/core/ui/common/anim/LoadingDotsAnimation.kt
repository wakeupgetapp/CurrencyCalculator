package com.wakeupgetapp.core.ui.common.anim

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun LoadingDotsAnimation() {
    val dots = listOf("", ".", "..", "...")
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val animatedIndex = infiniteTransition.animateValue(
        initialValue = 0,
        targetValue = dots.size,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Restart
        ),
        typeConverter = Int.VectorConverter, label = ""
    )
    val currentDot = dots[animatedIndex.value]

    Text(text = currentDot, color = Color.White)
}
