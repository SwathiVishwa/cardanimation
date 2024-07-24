package com.app.cardsample.card

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.app.cardsample.R
import com.app.cardsample.ui.theme.Pink
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun CardView(card: Card, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(100.dp, 150.dp)
    ) {
        Image(
            painter = painterResource(id = card.id),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
fun ShuffleCards() {
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
            shuffledCards.forEachIndexed { index, card ->
                val offsetX = animatedOffsets[index].value
                CardView(
                    card,
                    modifier = Modifier
                        .offset { IntOffset(offsetX.roundToInt(), 0) }
                )
            }
        }
    }
}
