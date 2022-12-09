package com.eygraber.compose.colorpicker

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.SweepGradientShader
import androidx.compose.ui.graphics.toPixelMap

/**
 * A color wheel with an [ImageBitmap] that draws a circular color wheel of the specified diameter.
 */
internal class ColorWheel(
  diameter: Int,
  radius: Float,
  alpha: Float,
  brightness: Float
) {
  private fun Color.applyBrightnessAndAlpha(alpha: Float, brightness: Float) =
    copy(
      red = red * brightness,
      green = green * brightness,
      blue = blue * brightness,
      alpha = alpha
    )

  private val sweepGradient = SweepGradientShader(
    colors = listOf(
      Color.Red.applyBrightnessAndAlpha(alpha, brightness),
      Color.Magenta.applyBrightnessAndAlpha(alpha, brightness),
      Color.Blue.applyBrightnessAndAlpha(alpha, brightness),
      Color.Cyan.applyBrightnessAndAlpha(alpha, brightness),
      Color.Green.applyBrightnessAndAlpha(alpha, brightness),
      Color.Yellow.applyBrightnessAndAlpha(alpha, brightness),
      Color.Red.applyBrightnessAndAlpha(alpha, brightness)
    ),
    colorStops = null,
    center = Offset(radius, radius)
  )

  private val saturationGradient = RadialGradientShader(
    colors = listOf(
      Color.White.applyBrightnessAndAlpha(alpha, brightness),
      Color.Transparent
    ),
    center = Offset(radius, radius),
    radius = radius
  )

  val image = ImageBitmap(diameter, diameter).also { imageBitmap ->
    val canvas = Canvas(imageBitmap)
    val center = Offset(radius, radius)
    val sweepPaint = Paint().apply {
      shader = sweepGradient
    }

    val saturationPaint = Paint().apply {
      shader = saturationGradient
    }

    canvas.drawCircle(center, radius, sweepPaint)
    canvas.drawCircle(center, radius, saturationPaint)
  }

  private val pixelMap by lazy {
    image.toPixelMap()
  }

  /**
   * @return the matching color for [position] inside [ColorWheel], or `null` if there is no color
   * or the color is partially transparent.
   */
  fun colorForPosition(position: Offset): Color {
    val x = position.x.toInt()
    val y = position.y.toInt()
    with(pixelMap) {
      if(x !in 0 until width || y !in 0 until height) return Color.Unspecified
      return this[x, y].takeIf { it.alpha > 0F } ?: Color.Unspecified
    }
  }
}
