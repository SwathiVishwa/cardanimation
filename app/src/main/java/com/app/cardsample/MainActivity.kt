package com.app.cardsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.app.cardsample.card.ShuffleCards
import com.app.cardsample.ui.theme.CardSampleTheme

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

fun halfCircleShape(): Shape = GenericShape { size, _ ->
    // Draw the half circle
    moveTo(0f, size.height)
    lineTo(size.width, size.height)
    arcTo(
        rect = Rect(
            left = 0f,
            top = size.height / 2,
            right = size.width,
            bottom = size.height + size.width / 2
        ),
        startAngleDegrees = 0f,
        sweepAngleDegrees = 180f,
        forceMoveTo = false
    )
}