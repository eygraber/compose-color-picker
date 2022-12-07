package com.eygraber.compose.colorpicker.sample

actual fun Float.toFixedDecimalCount(digits: Int): String = asDynamic().toFixed(digits).toString()
