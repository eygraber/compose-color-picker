import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeViewport
import com.eygraber.compose.colorpicker.sample.Sample

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
  ComposeViewport {
    Sample(
      modifier = Modifier.fillMaxSize(),
    )
  }
}
