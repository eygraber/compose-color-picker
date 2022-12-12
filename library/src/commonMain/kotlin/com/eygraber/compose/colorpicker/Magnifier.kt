package com.eygraber.compose.colorpicker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
internal fun Magnifier(
  transitionData: MagnifierTransitionData,
  magnifier: ColorPicker.Magnifier.Default,
  position: () -> Offset,
  color: () -> Color
) {
  val offsetModifier = Modifier.offset {
    val (x, y) = position()

    IntOffset(
      (x - magnifier.width.roundToPx() / 2).toInt(),
      // Align with the center of the selection circle
      (y - (magnifier.height.roundToPx() + magnifier.selectionDiameter.roundToPx()) / 2).toInt()
    )
  }

  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = offsetModifier
      .requiredHeight(magnifier.height)
      .requiredWidth(magnifier.width)
      .alpha(transitionData.alpha)
  ) {
    MagnifierPill(
      magnifier,
      color,
      modifier = Modifier.width(transitionData.pillWidth)
    )

    MagnifierSelectionCircle(
      modifier = Modifier.requiredSize(transitionData.selectionDiameter)
    )
  }
}

/**
 * Label representing the currently selected [colorProvider], with [Text] representing the hex code and a
 * square at the start showing the [colorProvider].
 */
@Composable
private fun MagnifierPill(
  options: ColorPicker.Magnifier.Default,
  colorProvider: () -> Color,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier,
    shape = options.popupShape
  ) {
    Row {
      Box(
        modifier = Modifier
          .height(options.pillHeight)
          .weight(options.pillColorWidthWeight)
          .drawBehind {
            drawRect(colorProvider())
          }
      )

      MagnifierLabel(options, colorProvider)
    }
  }
}

@Composable
private fun RowScope.MagnifierLabel(
  options: ColorPicker.Magnifier.Default,
  colorProvider: () -> Color,
) {
  val text = colorProvider().toHexString(options.showAlphaHex)
  val textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
  Text(
    text = text,
    modifier = Modifier
      .weight(options.pillHexWidthWeight)
      .padding(options.hexPadding),
    style = textStyle,
    maxLines = 1
  )
}

/**
 * Selection circle drawn over the currently selected pixel of the color wheel.
 */
@Composable
private fun MagnifierSelectionCircle(
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(CircleShape)
      .background(Color.Transparent)
      .border(
        border = BorderStroke(2.dp, SolidColor(Color.Black.copy(alpha = 0.75f))),
        shape = CircleShape
      )
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
