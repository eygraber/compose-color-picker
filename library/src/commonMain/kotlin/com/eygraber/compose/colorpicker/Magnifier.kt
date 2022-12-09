package com.eygraber.compose.colorpicker

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun Magnifier(
  options: ColorPicker.Magnifier.Options,
  visible: Boolean,
  position: Offset,
  color: Color
) {
  MagnifierTransition(
    visible,
    options,
    position
  ) { offset: Modifier, labelWidth: Dp, selectionDiameter: Dp, alpha: Float ->
    Column(
      modifier = offset
        .size(width = options.width, height = options.height)
        .alpha(alpha)
    ) {
      Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        MagnifierLabel(Modifier.size(labelWidth, options.labelHeight), options, color)
      }
      Box(
        Modifier.fillMaxWidth().height(options.selectionCircleDiameter),
        contentAlignment = Alignment.Center
      ) {
        MagnifierSelectionCircle(Modifier.requiredSize(selectionDiameter), color)
      }
    }
  }
}

/**
 * [Transition] that animates between [visible] states of the magnifier by animating the width of
 * the label, diameter of the selection circle, and alpha of the overall magnifier
 */
@Composable
private fun MagnifierTransition(
  visible: Boolean,
  options: ColorPicker.Magnifier.Options,
  position: Offset,
  content: @Composable (
    offset: Modifier,
    labelWidth: Dp,
    selectionDiameter: Dp,
    alpha: Float
  ) -> Unit
) {
  val transition = updateTransition(visible)

  val offset by transition.animateOffset(transitionSpec = { tween() }) {
    if(it) position else Offset.Zero
  }

  val labelWidth by transition.animateDp(transitionSpec = { tween() }) {
    if(it) options.width else 0.dp
  }
  val magnifierDiameter by transition.animateDp(transitionSpec = { tween() }) {
    if(it) options.selectionCircleDiameter else 0.dp
  }
  val alpha by transition.animateFloat(
    transitionSpec = {
      if(true isTransitioningTo false) {
        tween(delayMillis = 100, durationMillis = 200)
      }
      else {
        tween()
      }
    }
  ) {
    if(it) 1f else 0f
  }

  val offsetX = if(position != Offset.Zero) position.x else offset.x
  val offsetY = if(position != Offset.Zero) position.y else offset.y

  content(
    with(LocalDensity.current) {
      Modifier.offset(
        offsetX.toDp() - options.width / 2,
        // Align with the center of the selection circle
        offsetY.toDp() - (options.height + options.selectionCircleDiameter) / 2
      )
    },
    labelWidth,
    magnifierDiameter,
    alpha
  )
}

/**
 * Label representing the currently selected [color], with [Text] representing the hex code and a
 * square at the start showing the [color].
 */
@Composable
private fun MagnifierLabel(
  modifier: Modifier,
  options: ColorPicker.Magnifier.Options,
  color: Color
) {
  Surface(shape = options.popupShape) {
    Row(modifier) {
      Box(Modifier.weight(options.colorWidthWeight).fillMaxHeight().background(color))
      val text = color.toHexString(options.showAlpha)
      val textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
      Text(
        text = text,
        modifier = Modifier
          .weight(options.hexWidthWeight)
          .padding(top = 10.dp, bottom = 20.dp, end = 5.dp),
        style = textStyle,
        maxLines = 1
      )
    }
  }
}

/**
 * Selection circle drawn over the currently selected pixel of the color wheel.
 */
@Composable
private fun MagnifierSelectionCircle(modifier: Modifier, color: Color) {
  Surface(
    modifier,
    shape = CircleShape,
    color = color,
    border = BorderStroke(2.dp, SolidColor(Color.Black.copy(alpha = 0.75f))),
    content = {}
  )
}

/**
 * A [GenericShape] that draws a box with a triangle at the bottom center to indicate a popup.
 */
internal val MagnifierPopupShape = GenericShape { size, _ ->
  val width = size.width
  val height = size.height

  val arrowY = height * 0.8f
  val arrowXOffset = width * 0.4f

  addRoundRect(RoundRect(0f, 0f, width, arrowY, cornerRadius = CornerRadius(20f, 20f)))

  moveTo(arrowXOffset, arrowY)
  lineTo(width / 2f, height)
  lineTo(width - arrowXOffset, arrowY)
  close()
}
