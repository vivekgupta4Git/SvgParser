package com.vivekgupta.svgparser

import android.util.Xml
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import com.vivekgupta.svgparser.models.PathNodeInfo
import com.vivekgupta.svgparser.models.SvgInfo
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

/**
 *@author Vivek Gupta on 27-12-23
 */
object SvgParser {
    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): Pair<List<PathNodeInfo>, SvgInfo> {
      return  inputStream.use { stream ->
                val parser = Xml.newPullParser()
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
                parser.setInput(stream, null)
                parser.nextTag()
                 readSvg(parser)
            }

    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readSvg(parser: XmlPullParser): Pair<List<PathNodeInfo>, SvgInfo> {
        var width = 24f
        var height = 24f
        var viewBoxHeight = 24f
        var viewBoxWidth = 24f
        val svgPathNodes = mutableListOf<PathNodeInfo>()
        parser.require(XmlPullParser.START_TAG, null, "svg")
        var count = 1
        while (parser.eventType != XmlPullParser.END_TAG) {
            when (parser.name) {
                "svg" -> {
                    val attrWidth: String? = parser.getAttributeValue(null, "width")
                    val attrHeight: String? = parser.getAttributeValue(null, "height")
                    val attrViewBox : String? = parser.getAttributeValue(null,"viewBox")
                    attrWidth?.let { width = it.toFloat() }
                    attrHeight?.let { height = it.toFloat() }
                    attrViewBox?.let { vbox ->
                        val values = vbox.split(' ').map { it.toFloat() }
                        if(values.size == 4)
                        {
                            viewBoxWidth = values[2]
                            viewBoxHeight = values[3]
                        }
                    }
                }
                "path" -> {
                    svgPathNodes.add(readPath(parser, count))
                }

            }
            if (parser.name != null)
                ++count
            parser.next()
        }

        return Pair(svgPathNodes.toList(), SvgInfo(width = width,height=height, viewBoxHeight = viewBoxHeight, viewBoxWidth = viewBoxWidth))
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readPath(parser: XmlPullParser, count: Int):PathNodeInfo {
        val pathNodeInfo = PathNodeInfo(pathString = "")
        pathNodeInfo.name = "path_$count"
        while (parser.eventType != XmlPullParser.END_TAG) {
            when (parser.name) {
                "path" -> {
                    for(i in 0 until parser.attributeCount){
                        val attrValue = parser.getAttributeValue(i)
                        when(parser.getAttributeName(i)){
                            "d" -> pathNodeInfo.pathString = attrValue ?: ""
                            "fill"->  pathNodeInfo.fill = parser.readColor("fill")
                            "fill-opacity" -> pathNodeInfo.fillAlpha = attrValue?.toFloatOrNull() ?: 1f
                            "stroke-opacity" -> pathNodeInfo.strokeAlpha = attrValue?.toFloatOrNull() ?: 1f
                            "stroke"->pathNodeInfo.stroke = parser.readColor("stroke")
                            "stroke-width" ->   pathNodeInfo.strokeLineWidth = attrValue?.toFloatOrNull() ?: 1f
                            "stroke-linecap" -> {
                                                  pathNodeInfo.strokeLineCap = when (parser.readString("stroke-linecap").lowercase()) {
                                                      "round" -> StrokeCap.Round
                                                      "square" -> StrokeCap.Square
                                                      else -> StrokeCap.Butt
                                                }
                                            }
                            "stroke-linejoin" -> {
                                pathNodeInfo.strokeLineJoin = when (parser.readString("stroke-linejoin").lowercase()) {
                                      "round" -> StrokeJoin.Round
                                     "bevel" -> StrokeJoin.Bevel
                                    else -> StrokeJoin.Miter
                                 }
                            }
                            "trimPathStart" -> pathNodeInfo.trimPathStart = attrValue?.toFloatOrNull() ?: 0f
                            "trimPathEnd" -> pathNodeInfo.trimPathEnd =  attrValue?.toFloatOrNull() ?: 0f
                            "trimPathOffset"-> pathNodeInfo.trimPathOffset =  attrValue?.toFloatOrNull() ?: 0f
                        }
                    }
                }
            }
            parser.next()
        }
        return pathNodeInfo
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun XmlPullParser.readColor(attrName : String): Brush {
        val attr = getAttributeValue(null, attrName)
        return SolidColor(
            if (attr.startsWith("#"))
                hexToColor(attr)
            else
                stringToColor(attr)
        )
    }
    private fun hexToColor(hexColor: String): Color {
        val inputs = hexColor.trimStart('#')

        val red = inputs.substring(0, 2).toInt(16) / 255.0f
        val green = inputs.substring(2, 4).toInt(16) / 255.0f
        val blue = inputs.substring(4, 6).toInt(16) / 255.0f

        val alpha = if (inputs.length == 8) {
            inputs.substring(6, 8).toInt(16) / 255.0f
        } else {
            1.0f
        }

        return if (alpha == 1.0f) Color(red, green, blue) else Color(red, green, blue, alpha)
    }
    private fun stringToColor(colorString: String): Color {
        return when (colorString.lowercase()) {
            "pink" -> Color(0xFFFFC0CB)
            "purple" -> Color(0xFF800080)
            "orange" -> Color(0xFFFFA500)
            "black" -> Color.Black
            "white" -> Color.White
            "red" -> Color.Red
            "green" -> Color.Green
            "blue" -> Color.Blue
            "yellow" -> Color.Yellow
            "gray" -> Color.Gray
            "darkgray" -> Color.DarkGray
            "cyan" -> Color.Cyan
            "magenta" -> Color.Magenta
            "", "none", "lightgray" -> Color.LightGray
            else -> throw IllegalArgumentException("Unsupported Named color: $colorString")
        }
    }
    @Throws(IOException::class, XmlPullParserException::class)
    private fun  XmlPullParser.readFloat(attrName: String) = getAttributeValue(null,attrName).toFloatOrNull() ?: 0f
    @Throws(IOException::class, XmlPullParserException::class)
    private fun XmlPullParser.readString(attrName: String) = getAttributeValue(null,attrName) ?: ""

}