package com.example.finalassignment.utils

import android.content.Context
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.finalassignment.R
import com.example.finalassignment.screens.TextUI
import java.util.Locale

fun updateResources(
    context: Context,
    languageCode: String
): Context {
    val locale = Locale(languageCode).also {
        Locale.setDefault(it)
    }

    val resources = context.resources

    val configuration = resources.configuration.apply {
        this.locale = locale
        setLayoutDirection(locale)
    }

    resources.updateConfiguration(configuration, resources.displayMetrics)
    return context
}


@Composable
fun shimmerBrush(showShimmer: Boolean = true,targetValue:Float = 1000f): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f),
        )

        val transition = rememberInfiniteTransition(label = "")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(800), repeatMode = RepeatMode.Reverse
            ),
            label = "",
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent,Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}

@Composable
fun TitleTextUI(modifier: Modifier = Modifier, title: String) {
    Text(
        modifier = modifier,
        color = MaterialTheme.colorScheme.secondary,
        text = title,
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Start,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    )
}

@Composable
fun DescriptionTextUI(modifier: Modifier = Modifier, description: String) {
    Text(
        modifier = modifier,
        color = MaterialTheme.colorScheme.tertiary,
        text = description,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Start,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    )
}