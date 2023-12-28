package com.vivekgupta.svgparser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.Group
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vivekgupta.svgparser.models.PathNodeInfo
import com.vivekgupta.svgparser.models.SvgInfo
import com.vivekgupta.svgparser.ui.theme.SvgParserTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inputStream = this.resources.openRawResource(R.raw.new_animation_heart)
         val infoPair = SvgParser.parse(inputStream)

        setContent {
            val infiniteTransition = rememberInfiniteTransition()
            val alpha by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 0.2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(200),
                    repeatMode = RepeatMode.Reverse
                )
            )
            val alpha2 by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(200),
                    repeatMode = RepeatMode.Reverse
                )
            )
            val alpha3 by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 0f,
                animationSpec = infiniteRepeatable(
                    animation = tween(200),
                    repeatMode = RepeatMode.Reverse
                )
            )
            SvgParserTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val vectorPaint = rememberVectorPainter(
                        defaultWidth = infoPair.second.width.dp,
                        defaultHeight = infoPair.second.height.dp,
                        viewportWidth = infoPair.second.viewBoxWidth,
                        viewportHeight = infoPair.second.viewBoxHeight,
                        autoMirror = false
                    ) { _, _ ->

                        Path(pathData = PathParser().parsePathString(
                            "M348.4,258.84C348.55,258.94 364.23,269.03 386.94,272.04C416.44,275.95 435.02,268.09 448.06,248.2C448.38,245.7 449.49,238.41 451.99,233.18C453.13,230.79 455.99,229.78 458.38,230.92C460.77,232.06 461.78,234.93 460.64,237.31C459.54,239.6 458.74,242.76 458.21,245.46C458.42,245.53 458.64,245.62 458.85,245.71C461.62,243.79 464.68,240.83 466.09,236.55C466.92,234.04 469.63,232.67 472.14,233.5C474.66,234.34 476.02,237.04 475.19,239.56C473.4,244.97 470.1,248.93 466.73,251.75C471.4,250.56 474.35,247.28 474.57,247.03C476.28,245.03 479.3,244.78 481.31,246.49C483.33,248.2 483.58,251.23 481.87,253.25C478.57,257.14 470.58,262.81 460.59,261.6C460.59,261.6 432.86,299.74 383.67,290.12C368.3,287.11 355.58,283.06 347.63,279.26C341.94,276.54 338.62,274.38 338.31,274.17C334.08,271.38 332.9,265.67 335.7,261.45C338.49,257.21 344.17,256.04 348.4,258.84Z"
                        ).toNodes(), fill = SolidColor(Color(0xFFD11313)), fillAlpha = alpha2)
                        Path(pathData = PathParser().parsePathString(
                            "M465.516 250.302C469.315 247.123 471.969 243.308 473.404 238.963C473.905 237.454 473.078 235.797 471.554 235.292C470.818 235.046 470.031 235.105 469.335 235.456C468.642 235.805 468.127 236.404 467.882 237.141C466.592 241.046 463.839 244.545 459.919 247.254C459.385 247.625 458.694 247.693 458.095 247.434C457.931 247.363 457.765 247.296 457.598 247.239C456.708 246.929 456.182 246.012 456.362 245.095C456.851 242.608 457.692 239.096 458.934 236.5C459.268 235.8 459.312 235.013 459.052 234.279C458.794 233.544 458.266 232.959 457.566 232.624C456.12 231.933 454.379 232.548 453.688 233.994C451.277 239.035 450.201 246.324 449.93 248.437C449.892 248.72 449.792 248.991 449.636 249.229C435.859 270.243 416.445 277.854 386.687 273.908C363.593 270.847 347.53 260.522 347.37 260.418C347.366 260.415 347.363 260.412 347.359 260.41C345.736 259.336 343.795 258.962 341.89 259.355C339.985 259.746 338.345 260.858 337.271 262.485C335.055 265.837 335.986 270.372 339.347 272.595C339.485 272.686 342.776 274.849 348.447 277.559C356.889 281.591 369.856 285.496 384.028 288.267C431.418 297.532 458.797 260.865 459.069 260.491C459.471 259.94 460.139 259.647 460.819 259.729C470.188 260.861 477.552 255.42 480.426 252.03C480.93 251.438 481.173 250.687 481.106 249.91C481.042 249.133 480.681 248.431 480.085 247.929C478.873 246.898 477.04 247.045 475.984 248.275C475.629 248.68 472.362 252.264 467.189 253.578C466.338 253.793 465.446 253.393 465.046 252.609C464.913 252.345 464.843 252.061 464.839 251.781C464.834 251.223 465.069 250.678 465.516 250.302ZM483.306 254.466C480.054 258.302 471.888 264.379 461.44 263.577C456.821 269.289 428.891 300.877 383.305 291.964C368.833 289.135 355.537 285.124 346.823 280.959C341.018 278.186 337.635 275.978 337.268 275.737C332.174 272.372 330.761 265.498 334.125 260.405C335.753 257.938 338.241 256.252 341.131 255.659C344.022 255.065 346.968 255.634 349.431 257.257C349.761 257.469 365.178 267.253 387.186 270.171C415.145 273.878 433.382 266.89 446.247 247.518C446.673 244.383 447.828 237.508 450.286 232.366C451.875 229.044 455.868 227.634 459.191 229.22C460.8 229.989 462.014 231.339 462.607 233.021C463.201 234.702 463.104 236.516 462.337 238.126C461.9 239.036 461.514 240.105 461.184 241.204C462.628 239.614 463.671 237.859 464.301 235.958C464.859 234.262 466.045 232.89 467.639 232.085C469.231 231.284 471.043 231.151 472.735 231.711C476.23 232.868 478.133 236.646 476.981 240.148C476.526 241.524 475.967 242.853 475.306 244.134C477.611 243.026 480.457 243.297 482.524 245.052C484.052 246.345 484.854 248.189 484.882 250.05C484.908 251.609 484.391 253.183 483.306 254.466Z"
                        ).toNodes(), fill = SolidColor(Color(0xFFAD0000)), fillAlpha = alpha2)

                        infoPair.first.asSequence()
                            .forEach { node  ->
                                when(node.name){
                                    "path_4","path_5"-> node.ToPath(alpha3)
                                    "path_52","path_53"-> node.ToPath(alpha2)
                                    "path_51" -> node.ToPath(alpha = alpha)
                                    else -> node.ToPath()
                                }
                            }

                    }

                    Image(painter = vectorPaint, contentDescription = null)

                }
            }
        }
    }
}

@Composable
fun PathNodeInfo.ToPath() {
    Path(
        pathData = PathParser().parsePathString(pathString)
            .toNodes(),
        fill = fill,
        fillAlpha = fillAlpha,
        stroke = stroke,
        strokeAlpha = strokeAlpha,
        strokeLineWidth = strokeLineWidth,
        strokeLineCap = strokeLineCap,
        strokeLineJoin = strokeLineJoin,
        strokeLineMiter = strokeLineMiter,
        trimPathStart = trimPathStart,
        trimPathEnd = trimPathEnd,
        trimPathOffset = trimPathOffset
    )

}
@Composable
fun PathNodeInfo.ToPath(alpha : Float) {
    Path(
        pathData = PathParser().parsePathString(pathString)
            .toNodes(),
        fill = fill,
        fillAlpha = alpha,
        stroke = stroke,
        strokeAlpha = strokeAlpha,
        strokeLineWidth = strokeLineWidth,
        strokeLineCap = strokeLineCap,
        strokeLineJoin = strokeLineJoin,
        strokeLineMiter = strokeLineMiter,
        trimPathStart = trimPathStart,
        trimPathEnd = trimPathEnd,
        trimPathOffset = trimPathOffset
    )

}