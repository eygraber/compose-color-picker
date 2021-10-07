package com.eygraber.compose.colorpicker.sample

import androidx.compose.ui.window.singleWindowApplication
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi fun main() {
  singleWindowApplication(title = "Color Picker Sample") {
    Sample()
  }
}
