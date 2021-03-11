package com.eygraber.compose.colorpicker.sample

expect class DecimalFormat(pattern: String) {
  fun format(obj: Any): String
}
