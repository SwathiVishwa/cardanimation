package com.app.cardsample.card

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.app.cardsample.R
import com.app.cardsample.ui.theme.Pink
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CardView(card: Card, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(100.dp, 150.dp)

    ) {
        Image(
            painter = painterResource(id = card.id),
            contentDescription = null,
        )
    }
}

@Composable
fun ShuffleCards() {
    val context = LocalContext.current
    val cards = remember { getCards() }
    var shuffledCards by remember { mutableStateOf(cards) }

    val animationScope = rememberCoroutineScope()

    // States for shuffling and animated positions
    var shuffled by remember { mutableStateOf(false) }
    val animatedOffsets = remember { cards.map { Animatable(0f) } }

    // Shuffle the cards and animate the positions
    LaunchedEffect(shuffled) {
        animationScope.launch {
            animatedOffsets.forEachIndexed { index, animatable ->
                val targetValue = if (shuffled) (index - 5) * 50f else 0f
                animatable.animateTo(
                    targetValue = targetValue,
                    animationSpec = tween(durationMillis = 20)
                )
            }
        }
    }
    var image by remember { mutableStateOf<Bitmap?>(null) }
    LaunchedEffect(R.drawable.clubs1) {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(R.drawable.clubs1)
            .build()

        // Step 3: Execute the image request and get the result
        val result = (loader.execute(request) as SuccessResult).drawable

        // Step 4: Convert the drawable to a bitmap
        image = (result as BitmapDrawable).bitmap
    }

    val radius = 200.dp
    val angleStep = 180f / (shuffledCards.size - 1)
    val density = LocalDensity.current.density

    // Box to contain the card stack and shuffle button
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Pink),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_bg),
            contentDescription = "bg",
            modifier = Modifier.fillMaxSize()
        )

        // Shuffle button
        Button(
            onClick = {
                shuffledCards = shuffleCards(cards)
                shuffled = !shuffled
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text("Shuffle Cards")
        }

        // Cards stack
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val centerX = size.width / 2
                val centerY = size.height / 2

                shuffledCards.forEachIndexed { index, card ->
                    val angle = 180f + angleStep * index
                    val radian = Math.toRadians(angle.toDouble())
                    val x = (centerX + radius.toPx() * cos(radian)).toFloat()
                    val y = (centerY + radius.toPx() * sin(radian)).toFloat()


                    drawContext.canvas.nativeCanvas.apply {
                        save()
                        rotate(angle, x, y)
                        image?.let {
                            drawBitmap(
                                it,
                                x - (image!!.width / 2),
                                y - (image!!.height / 2),
                                android.graphics.Paint().apply {
                                    isAntiAlias = true
                                }
                            )
                        }
                        restore()
                    }
                }
            }


        }
    }
}

