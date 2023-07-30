@file:Suppress("MissingPackageDeclaration")

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.eygraber.compose.colorpicker.sample.Sample
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
  onWasmReady {
    CanvasBasedWindow("ColorPicker") {
      Sample()
    }
  }
}
