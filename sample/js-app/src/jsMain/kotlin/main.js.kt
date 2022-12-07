@file:Suppress(
  "INVISIBLE_MEMBER",
  "INVISIBLE_REFERENCE",
  "EXPOSED_PARAMETER_TYPE",
  "MissingPackageDeclaration"
) // TODO: ComposeWindow and ComposeLayer are internal

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ComposeWindow
import com.eygraber.compose.colorpicker.sample.Sample
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.skiko.wasm.onWasmReady
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLStyleElement
import org.w3c.dom.HTMLTitleElement

fun main() {
  onWasmReady {
    BrowserViewportWindow("ColorPicker") {
      Sample()
    }
  }
}

/**
 * Borrowed with love from [Michael Paus](https://github.com/mipastgt/JavaForumStuttgartTalk2022/blob/5193c1a2bd996b6f175243c143aa5140fb3e8b82/PolySpiralMpp/src/jsMain/kotlin/BrowserViewportWindow.kt)
 */
@Suppress("FunctionName")
private fun BrowserViewportWindow(
  title: String = "Untitled",
  content: @Composable ComposeWindow.() -> Unit
) {
  val htmlHeadElement = document.head!!
  htmlHeadElement.appendChild(
    (document.createElement("style") as HTMLStyleElement).apply {
      type = "text/css"
      appendChild(
        document.createTextNode(
          """
              |html, body {
              |    overflow: hidden;
              |    margin: 0 !important;
              |    padding: 0 !important;
              |}
              |#ComposeTarget {
              |    outline: none;
              |}
          """.trimMargin()
        )
      )
    }
  )

  fun HTMLCanvasElement.fillViewportSize() {
    setAttribute("width", "${window.innerWidth}")
    setAttribute("height", "${window.innerHeight}")
  }

  val canvas = (document.getElementById("ComposeTarget") as HTMLCanvasElement).apply {
    fillViewportSize()
  }

  ComposeWindow().apply {
    window.addEventListener("resize", {
      canvas.fillViewportSize()
      layer.layer.attachTo(canvas)
      val scale = layer.layer.contentScale
      layer.setSize((canvas.width / scale).toInt(), (canvas.height / scale).toInt())
      layer.layer.needRedraw()
    })

    // WORKAROUND: ComposeWindow does not implement `setTitle(title)`
    val htmlTitleElement =
      (
        htmlHeadElement
          .getElementsByTagName("title")
          .item(0)
          ?: document
            .createElement("title")
            .also { htmlHeadElement.appendChild(it) }
        ) as HTMLTitleElement

    htmlTitleElement.textContent = title

    setContent {
      content(this)
    }
  }
}
