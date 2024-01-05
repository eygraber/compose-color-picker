package com.eygraber.compose.colorpicker

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.hypot

internal fun Color.toHexString(includeAlpha: Boolean = true): String = buildString {
  append("#")
  if(includeAlpha) {
    append((alpha * 255).toInt().toString(radix = 16).padStart(length = 2, padChar = '0').uppercase())
  }
  append((red * 255).toInt().toString(radix = 16).padStart(length = 2, padChar = '0').uppercase())
  append((green * 255).toInt().toString(radix = 16).padStart(length = 2, padChar = '0').uppercase())
  append((blue * 255).toInt().toString(radix = 16).padStart(length = 2, padChar = '0').uppercase())
}

internal fun Offset.translate(
  newDiameter: Int,
  oldDiameter: Int,
): Offset {
  val multiplier = newDiameter / oldDiameter.toFloat()
  return Offset(
    x = x * multiplier,
    y = y * multiplier,
  )
}

internal fun Offset.clampToCircle(radius: Float): Offset {
  val dx = x - radius
  val dy = y - radius
  val d = hypot(dx, dy)
  return when {
    d > radius -> Offset(
      x = radius + dx * (radius - 2) / d,
      y = radius + dy * (radius - 2) / d,
    )
    else -> this
  }
}
