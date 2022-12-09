package com.eygraber.compose.colorpicker

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
public fun ColorPicker(
  modifier: Modifier = Modifier,
  alpha: Float = 1F,
  brightness: Float = 1F,
  magnifier: ColorPicker.Magnifier = ColorPicker.Magnifier.Options(),
  resetSelectedPosition: Boolean = false,
  onColorSelected: (Color) -> Unit
) {
  BoxWithConstraints(
    modifier = modifier
      .aspectRatio(1F)
  ) {
    val diameter = constraints.maxWidth
    val radius = diameter / 2F

    var previousDiameter by remember { mutableStateOf(diameter) }
    var selectedPosition by remember { mutableStateOf(Offset.Zero) }
    var selectedColor by remember { mutableStateOf(Color.Unspecified) }

    val isSelectedAndDiameterChanged =
      selectedPosition != Offset.Zero && diameter != previousDiameter

    if(resetSelectedPosition) {
      selectedPosition = Offset.Zero
    }
    else if(isSelectedAndDiameterChanged) {
      selectedPosition = selectedPosition.translate(
        newDiameter = diameter,
        oldDiameter = previousDiameter
      )

      previousDiameter = diameter
    }

    val colorWheel = remember(diameter, alpha, brightness) {
      ColorWheel(
        diameter = diameter,
        radius = radius,
        alpha = alpha,
        brightness = brightness
      ).apply {
        val currentColor = colorForPosition(selectedPosition)
        if(currentColor.isSpecified) {
          onColorSelected(currentColor)
        }
      }
    }

    val inputModifier = Modifier
      .pointerInput(diameter, alpha, brightness) {
        fun update(newPosition: Offset) {
          val clampedPosition = newPosition.clampToCircle(radius)
          if(clampedPosition != selectedPosition) {
            val newColor = colorWheel.colorForPosition(clampedPosition)
            if(newColor.isSpecified) {
              if(selectedColor != newColor) {
                selectedColor = newColor
                onColorSelected(newColor)
              }
              selectedPosition = clampedPosition
            }
          }
        }

        forEachGesture {
          awaitPointerEventScope {
            val down = awaitFirstDown()
            update(down.position)
            drag(down.id) { change ->
              change.consume()
              update(change.position)
            }
          }
        }
      }

    Box(
      modifier = inputModifier
    ) {
      Image(contentDescription = null, bitmap = colorWheel.image)
      val color = colorWheel.colorForPosition(selectedPosition)
      if(magnifier is ColorPicker.Magnifier.Options) {
        Magnifier(
          options = magnifier,
          visible = selectedPosition != Offset.Zero && color.isSpecified,
          position = selectedPosition,
          color = color
        )
      }
    }
  }
}

public object ColorPicker {
  @Immutable
  public sealed class Magnifier {
    @Immutable
    public object None : Magnifier()

    @Immutable
    public data class Options(
      val width: Dp = 150.dp,
      val height: Dp = 100.dp,
      val labelHeight: Dp = 50.dp,
      val selectionCircleDiameter: Dp = 15.dp,
      val showAlpha: Boolean = true,
      val colorWidthWeight: Float = .25F,
      val hexWidthWeight: Float = .75F,
      val popupShape: GenericShape = MagnifierPopupShape
    ) : Magnifier()
  }
}
