package com.eygraber.compose.colorpicker.sample

import androidx.compose.desktop.Window
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi fun main() {
  Window(title = "Color Picker Sample") {
    Sample()
  }
}
