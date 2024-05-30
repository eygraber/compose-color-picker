package com.eygraber.compose.colorpicker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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

@Immutable
public data class ColorPickerState(
  val alpha: Float,
  val brightness: Float,
  val magnifier: ColorPicker.Magnifier,
  @Suppress("BooleanPropertyNaming") val resetSelectedPosition: Boolean,
  val colors: List<Color>,
)

@Composable
public fun rememberColorPickerState(
  alpha: Float = 1F,
  brightness: Float = 1F,
  magnifier: ColorPicker.Magnifier = ColorPicker.Magnifier.Default(),
  resetSelectedPosition: Boolean = false,
  @Suppress("UnstableCollections") colors: List<Color> = ColorWheel.DefaultColors,
): ColorPickerState =
  remember(alpha, brightness, magnifier, resetSelectedPosition, colors) {
    ColorPickerState(
      alpha,
      brightness,
      magnifier,
      resetSelectedPosition,
      colors,
    )
  }

@Composable
public fun ColorPicker(
  modifier: Modifier = Modifier,
  state: ColorPickerState = rememberColorPickerState(),
  onSelectedColor: (Color) -> Unit,
) {
  BoxWithConstraints(
    modifier = modifier
      .aspectRatio(1F),
  ) {
    val diameter = constraints.maxWidth
    val radius = diameter / 2F

    var previousDiameter by remember { mutableIntStateOf(diameter) }
    var selectedPosition by remember { mutableStateOf(Offset.Zero) }
    var selectedPositionBeforeReset by remember { mutableStateOf(Offset.Zero) }
    var selectedColor by remember { mutableStateOf(Color.Unspecified) }

    val isSelectedAndDiameterChanged =
      selectedPosition != Offset.Zero && diameter != previousDiameter

    if(state.resetSelectedPosition) {
      if(selectedPosition != Offset.Zero) {
        selectedPositionBeforeReset = selectedPosition
      }
      selectedPosition = Offset.Zero
    }
    else if(isSelectedAndDiameterChanged) {
      selectedPosition = selectedPosition.translate(
        newDiameter = diameter,
        oldDiameter = previousDiameter,
      )

      previousDiameter = diameter
    }

    val colorWheel = remember(diameter, state.alpha, state.brightness) {
      ColorWheel(
        diameter = diameter,
        radius = radius,
        alpha = state.alpha,
        brightness = state.brightness,
        colors = state.colors,
      ).apply {
        val currentColor = colorForPosition(selectedPosition)
        if(currentColor.isSpecified && currentColor != selectedColor) {
          selectedColor = currentColor
          onSelectedColor(currentColor)
        }
      }
    }

    val inputModifier = Modifier
      .pointerInput(diameter, state.alpha, state.brightness) {
        fun update(newPosition: Offset) {
          val clampedPosition = newPosition.clampToCircle(radius)
          if(clampedPosition != selectedPosition) {
            val newColor = colorWheel.colorForPosition(clampedPosition)
            if(newColor.isSpecified) {
              if(selectedColor != newColor) {
                selectedColor = newColor
                onSelectedColor(newColor)
              }
              selectedPosition = clampedPosition
            }
          }
        }

        awaitEachGesture {
          val down = awaitFirstDown()
          update(down.position)
          drag(down.id) { change ->
            change.consume()
            update(change.position)
          }
        }
      }

    Box(
      modifier = inputModifier,
    ) {
      Image(contentDescription = null, bitmap = colorWheel.image)

      if(state.magnifier is ColorPicker.Magnifier.Default) {
        val isMagnifierVisible = selectedColor.isSpecified && selectedPosition != Offset.Zero
        AnimatedVisibility(
          visible = isMagnifierVisible,
          enter = EnterTransition.None,
          exit = ExitTransition.None,
        ) {
          Magnifier(
            transitionData = updateMagnifierTransitionData(state.magnifier),
            magnifier = state.magnifier,
            position = {
              when(selectedPosition) {
                Offset.Zero -> selectedPositionBeforeReset
                else -> selectedPosition
              }
            },
            color = { selectedColor },
          )
        }
      }
    }
  }
}

@Suppress("ktlint:standard:max-line-length")
@Deprecated(
  message = "Use ColorPicker(Modifier, ColorPickerState, (Color) -> Unit)",
  level = DeprecationLevel.WARNING,
  replaceWith = ReplaceWith(
    "ColorPicker(modifier = modifier, state = rememberColorPickerState(alpha = alpha, brightness = brightness, magnifier = magnifier, resetSelectedPosition = resetSelectedPosition, ), onColorSelected, )",
  ),
)
@Composable
public fun ColorPicker(
  modifier: Modifier = Modifier,
  alpha: Float = 1F,
  brightness: Float = 1F,
  magnifier: ColorPicker.Magnifier = ColorPicker.Magnifier.Default(),
  resetSelectedPosition: Boolean = false,
  onSelectedColor: (Color) -> Unit,
) {
  ColorPicker(
    modifier = modifier,
    state = rememberColorPickerState(
      alpha = alpha,
      brightness = brightness,
      magnifier = magnifier,
      resetSelectedPosition = resetSelectedPosition,
    ),
    onSelectedColor,
  )
}

public object ColorPicker {
  @Immutable
  public sealed class Magnifier {
    @Immutable
    public data object None : Magnifier()

    @Immutable
    public data class Default(
      val width: Dp = 150.dp,
      val height: Dp = 100.dp,
      val pillHeight: Dp = 50.dp,
      val pillColorWidthWeight: Float = .25F,
      val pillHexWidthWeight: Float = .75F,
      val shouldShowAlphaHex: Boolean = true,
      // more padding on bottom to account for the default shape
      val hexPadding: PaddingValues = PaddingValues(
        end = 5.dp,
        top = 10.dp,
        bottom = 20.dp,
      ),
      val selectionDiameter: Dp = 15.dp,
      val popupShape: GenericShape = MagnifierPopupShape,
    ) : Magnifier()
  }
}
