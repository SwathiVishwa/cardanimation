package com.app.cardsample

import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.app.cardsample.card.ShuffleCards
import com.app.cardsample.ui.theme.CardSampleTheme
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CardSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShuffleCards()
                }
            }
        }
    }
}

@Composable
fun HalfCircleListView(items: List<String>) {
    val itemCount = items.size
    val radius = 200.dp
    val angleStep = 180f / (itemCount - 1)
    val density = LocalDensity.current.density

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val centerX = size.width / 2
            val centerY = size.height / 2

            items.forEachIndexed { index, item ->
                val angle = 180f + angleStep * index
                val radian = Math.toRadians(angle.toDouble())
                val x = (centerX + radius.toPx() * cos(radian)).toFloat()
                val y = (centerY + radius.toPx() * sin(radian)).toFloat()

                drawContext.canvas.nativeCanvas.apply {
                    save()
                    rotate(angle, x, y)
                    drawText(item, x, y, Paint().apply {
                        textSize = 40f * density
                        color = android.graphics.Color.BLACK
                        textAlign = Paint.Align.CENTER
                    })
                    restore()
                }
            }
        }
    }
}