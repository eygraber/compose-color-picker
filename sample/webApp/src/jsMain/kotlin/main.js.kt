import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.eygraber.compose.colorpicker.sample.Sample

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
  CanvasBasedWindow("ColorPicker") {
    Sample()
  }
}
