package com.eygraber.compose.colorpicker.sample

import java.text.DecimalFormat

private val digitCache = mutableMapOf<Int, DecimalFormat>()

actual fun Float.toFixedDecimalCount(digits: Int): String =
  digitCache.getOrPut(digits) { DecimalFormat("0.${"0".repeat(digits)}") }.format(this)
