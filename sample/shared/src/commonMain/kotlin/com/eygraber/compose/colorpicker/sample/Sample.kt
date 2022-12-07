package com.eygraber.compose.colorpicker.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.unit.dp
import com.eygraber.compose.colorpicker.ColorPicker
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun Sample() {
  SampleCard {
    val defaultColor = MaterialTheme.colorScheme.onSurface

    var selectedColor by remember { mutableStateOf(defaultColor) }
    var alpha by remember { mutableStateOf(1F) }
    var brightness by remember { mutableStateOf(1F) }
    var resetSelectedPosition by remember { mutableStateOf(false) }

    Column(
      modifier = Modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Controls(
        selectedColor = selectedColor,
        alpha = alpha,
        brightness = brightness,
        defaultColor = defaultColor,
        onSelectedColorChanged = { newSelectedColor ->
          selectedColor = newSelectedColor
        },
        onAlphaChanged = { newAlpha ->
          alpha = newAlpha
        },
        onBrightnessChanged = { newBrightness ->
          brightness = newBrightness
        },
        resetSelectedPosition = {
          resetSelectedPosition = true

          GlobalScope.launch {
            delay(500)
            resetSelectedPosition = false
          }
        }
      )

      ColorPicker(
        modifier = Modifier.weight(.66F),
        alpha = alpha,
        brightness = brightness,
        resetSelectedPosition = resetSelectedPosition,
        onColorSelected = { newSelectedColor ->
          selectedColor = when {
            newSelectedColor.isSpecified -> newSelectedColor
            else -> defaultColor
          }
        }
      )
    }
  }
}

@Composable
private fun ColumnScope.Controls(
  selectedColor: Color,
  defaultColor: Color,
  alpha: Float,
  brightness: Float,
  onSelectedColorChanged: (Color) -> Unit,
  onAlphaChanged: (Float) -> Unit,
  onBrightnessChanged: (Float) -> Unit,
  resetSelectedPosition: () -> Unit
) {
  Row(
    modifier = Modifier.weight(.33F).fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Column(
      verticalArrangement = Arrangement.SpaceAround
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Pick a color",
          color = selectedColor
        )

        IconButton(
          onClick = {
            onSelectedColorChanged(defaultColor)
            onAlphaChanged(1F)
            onBrightnessChanged(1F)
            resetSelectedPosition()
          }
        ) {
          Icon(
            imageVector = RefreshIcon,
            contentDescription = "Back to defaults"
          )
        }
      }

      Box(
        modifier = Modifier
          .size(75.dp)
          .background(selectedColor)
      )
    }

    Column(
      modifier = Modifier.align(Alignment.CenterVertically)
    ) {
      Text(text = "Alpha: ${alpha.toFixedDecimalCount(1)}")

      Slider(
        value = alpha,
        onValueChange = {
          onAlphaChanged(it)
        },
        valueRange = 0F..1F,
        steps = 0,
        colors = SliderDefaults.colors(thumbColor = selectedColor, activeTrackColor = selectedColor),
        modifier = Modifier.width(200.dp)
      )

      Text(text = "Brightness: ${brightness.toFixedDecimalCount(1)}")

      Slider(
        value = brightness,
        onValueChange = {
          onBrightnessChanged(it)
        },
        valueRange = 0F..1F,
        steps = 0,
        colors = SliderDefaults.colors(thumbColor = selectedColor, activeTrackColor = selectedColor),
        modifier = Modifier.width(200.dp)
      )
    }
  }
}

@Composable
private fun SampleCard(
  content: @Composable BoxScope.() -> Unit
) {
  MaterialTheme(
    colorScheme = darkColorScheme()
  ) {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(
          color = MaterialTheme.colorScheme.background
        )
    ) {
      Card(
        shape = MaterialTheme.shapes.small.copy(all = CornerSize(8.dp)),
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp)
      ) {
        Box(
          modifier = Modifier.padding(16.dp)
        ) {
          content()
        }
      }
    }
  }
}
