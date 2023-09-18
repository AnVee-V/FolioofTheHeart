package com.anvee.foliooftheheart

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerListItem(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if(isLoading) {
        Row(modifier = Modifier) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Color.LightGray)
            ) {
                Spacer(modifier = Modifier.height(2.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .padding(horizontal = 2.dp)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(1.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .height(15.dp)
                        .padding(2.dp)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    } else {
        contentAfterLoading()
    }
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "Shimmer")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000
            )
        ), label = "Shimmer offsetX"
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB4B4B4),
                Color(0xFF969696),
                Color(0xFF646464),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned {
        size = it.size
    }
}