package com.eygraber.compose.colorpicker

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal class MagnifierTransitionData(
  pillWidth: State<Dp>,
  selectionDiameter: State<Dp>,
  alpha: State<Float>
) {
  val pillWidth: Dp by pillWidth
  val selectionDiameter: Dp by selectionDiameter
  val alpha: Float by alpha
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun AnimatedVisibilityScope.updateMagnifierTransitionData(
  options: ColorPicker.Magnifier.Default
): MagnifierTransitionData {
  val labelWidth = transition.animateDp(transitionSpec = { tween() }) {
    if(it == EnterExitState.Visible) options.width else 0.dp
  }

  val magnifierDiameter = transition.animateDp(transitionSpec = { tween() }) {
    if(it == EnterExitState.Visible) options.selectionDiameter else 0.dp
  }

  val alpha = transition.animateFloat(transitionSpec = { tween() }) {
    if(it == EnterExitState.Visible) 1f else 0f
  }

  return remember(transition) {
    MagnifierTransitionData(labelWidth, magnifierDiameter, alpha)
  }
}
