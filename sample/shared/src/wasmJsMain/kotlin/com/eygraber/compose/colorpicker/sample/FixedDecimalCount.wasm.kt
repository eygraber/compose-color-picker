package com.eygraber.compose.colorpicker.sample

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("(f, d) => f.toFixed(d)")
external fun toFixed(f: Float, d: Int): String

actual fun Float.toFixedDecimalCount(digits: Int): String = toFixed(this, digits)
