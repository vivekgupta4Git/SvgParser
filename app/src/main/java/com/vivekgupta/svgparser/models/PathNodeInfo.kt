package com.vivekgupta.svgparser.models

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.DefaultFillType
import androidx.compose.ui.graphics.vector.DefaultTrimPathEnd
import androidx.compose.ui.graphics.vector.DefaultTrimPathOffset
import androidx.compose.ui.graphics.vector.DefaultTrimPathStart

/**
 *@author Vivek Gupta on 27-12-23
 */
data class PathNodeInfo(
    var pathString: String,
    var name: String = "path",
    var fill: Brush? = SolidColor(Color.LightGray),
    var fillAlpha: Float = 1f,
    var stroke: Brush? = null,
    var strokeAlpha: Float = 1f,
    var strokeLineWidth: Float = 1f,
    var strokeLineCap: StrokeCap = StrokeCap.Butt,
    var strokeLineJoin: StrokeJoin = StrokeJoin.Bevel,
    var strokeLineMiter: Float = 1f,
    var pathFillType: PathFillType = DefaultFillType,
    var trimPathStart: Float = DefaultTrimPathStart,
    var trimPathEnd: Float = DefaultTrimPathEnd,
    var trimPathOffset: Float = DefaultTrimPathOffset
)
