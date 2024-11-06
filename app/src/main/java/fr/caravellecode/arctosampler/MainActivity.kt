package fr.caravellecode.arctosampler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fr.caravellecode.arctosampler.ui.theme.ArcToSamplerTheme

val NICE_COLOR = Color(0xFF206F7D)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArcToSamplerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ShowArcTo(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun PlayWithBlendModePreview() {
    ArcToSamplerTheme {
        PlayWithBlendMode()
    }
}

@Preview
@Composable
private fun PlayWithDrawStyleAndUseCenterPreview() {
    ArcToSamplerTheme {
        PlayWithDrawStyleAndUseCenter()
    }
}

@Composable
fun PlayWithDrawStyleAndUseCenter() {
    // Generate list of all pairs
    val drawStyles: List<DrawStyle> = listOf(Fill, Stroke())
    val useCenterValues = listOf(true, false)
    val allPairs: List<Pair<DrawStyle, Boolean>> = drawStyles.cartesianProduct(useCenterValues)

    Surface {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "All possible values of drawStyle and useCenter",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier
                    .background(color = Color.White)
                    .fillMaxWidth()
            ) {
                allPairs.map { pair: Pair<DrawStyle, Boolean> ->
                    val drawStyleValue = pair.first
                    val useCenterValue = pair.second

                    val drawStyleText = if (drawStyleValue is Fill) "Fill" else "Stroke"

                    Column(
                        modifier = Modifier
                            .border(color = Color.LightGray, width = Dp.Hairline)
                            .padding(4.dp)
                    ) {
                        Text(
                            text = "style=$drawStyleText",
                            style = MaterialTheme.typography.labelSmall,
                        )
                        Text(
                            text = "useCenter=$useCenterValue",
                            style = MaterialTheme.typography.labelSmall,
                        )
                        Box(modifier = Modifier
                            .size(80.dp)
                            .padding(8.dp)
                            .border(color = Color.LightGray, width = Dp.Hairline)
                            .padding(4.dp)
                            .drawBehind {

                                drawArc(
                                    style = drawStyleValue,
                                    useCenter = useCenterValue,
                                    //
                                    color = NICE_COLOR,
                                    startAngle = 0.0f,
                                    sweepAngle = -90.0f,
                                    topLeft = Offset.Zero,
                                )
                            }

                        ) // box content intentionally left empty
                    }

                }
            }

        }
    }
}

@Preview
@Composable
private fun drawArcInBoxPreview() {
    ArcToSamplerTheme {
        ArcInBox(startAngleValue = -90f, sweepAngleValue = 30f)
    }
}

@Composable
fun ArcInBox(startAngleValue: Float, sweepAngleValue: Float) {
    Box(modifier = Modifier
        .size(100.dp) // need a size here, otherwise Box not shown
        .padding(8.dp)
        .border(color = Color.LightGray, width = Dp.Hairline)
        .padding(4.dp)
        .drawBehind {

            drawArc(
                topLeft = Offset.Zero,
                color = Color.Cyan,
                useCenter = true,
                startAngle = startAngleValue,
                sweepAngle = sweepAngleValue,
                size = size, // use size of box
                style = Fill,
            )
        }

    ) // box content intentionally left empty
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PlayWithBlendMode() {
    val allBlendModes = listOf(
        BlendMode.Clear,
        BlendMode.Color,
        BlendMode.Darken,
        BlendMode.Difference,
        BlendMode.DstAtop,
        BlendMode.DstIn,
        BlendMode.DstOut,
        BlendMode.DstOver,
        BlendMode.Hue,
        BlendMode.Luminosity,
        BlendMode.Modulate,
        BlendMode.Multiply,
        BlendMode.Plus,
        BlendMode.Saturation,
        BlendMode.SrcAtop,
        BlendMode.Xor,
    )
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                text = "Some examples of BlendMode",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            FlowRow (
                modifier = Modifier
                    .background(color = Color.White)
                    .fillMaxWidth()
            ) {
                allBlendModes.map { value: BlendMode ->
                    Column(
                        modifier = Modifier
                            .border(color = Color.LightGray, width = Dp.Hairline)
                            .padding(4.dp)
                    ) {
                        Text(
                            text = "blendMode=",
                            style = MaterialTheme.typography.labelSmall,
                        )
                        Text(
                            text = "$value",
                            style = MaterialTheme.typography.labelSmall,
                        )
                        Box(modifier = Modifier
                            .size(80.dp)
                            .padding(8.dp)
                            .border(color = Color.LightGray, width = Dp.Hairline)
                            .padding(4.dp)
                            .background(Color.LightGray)
                            .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                            .drawBehind {
                                drawRect(
                                    topLeft = Offset(size.width / 4, size.height / 4),
                                    size = Size(size.width / 2, size.height / 2),
                                    color = Color.Red
                                )
                                drawArc(
                                    style = Fill,
                                    useCenter = true,
                                    //
                                    color = NICE_COLOR,
                                    startAngle = 0.0f,
                                    sweepAngle = -90.0f,
                                    topLeft = Offset.Zero,
                                    blendMode = value,
                                    alpha = 0.7f,
                                )
                            }

                        ) // box content intentionally left empty
                    }

                }
            }

        }
    }
}

fun <S, T> List<S>.cartesianProduct(other: List<T>): List<Pair<S, T>> = this.flatMap { s ->
    List(other.size) { s }.zip(other)
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShowArcTo(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "arcTo Sampler with various start angles",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
        )
        val blueRedBrush = remember {
            Brush.linearGradient(
                colorStops = arrayOf(Pair(0f, Color.Red), Pair(20f, Color.Blue)),
                tileMode = TileMode.Mirror,
                start = Offset.Zero,
                end = Offset(40f, 40f)
            )
        }
        val topCenterOffset = Offset(50f, 50f)
        val endOffset = Offset(200f, 250f)
        val rectangleSize = remember {
            Size(
                width = endOffset.x - topCenterOffset.x, height = endOffset.y - topCenterOffset.y
            )
        }
        val dotRadius = 6f
        val dotColor = Color.DarkGray
        val constructionColor = Color.LightGray
        FlowRow (modifier = Modifier.fillMaxWidth()) {

        // Loop on start angle values
        listOf(
            -360f, 0f, 90f, -90f, 180f, -180f,
            240f
            , -240f
            ).map { startAngle ->
            Box(modifier = Modifier
                .size(90.dp, 100.dp)
                .border(1.dp, color = Color.LightGray)
                .clipToBounds()
                .drawBehind {
                    drawCircle(
                        color = constructionColor,
                        center = topCenterOffset,
                        radius = dotRadius
                    )
                    drawRect(
                        color = constructionColor,
                        topLeft = topCenterOffset,
                        size = rectangleSize,
                        style = Stroke()
                    )
                    // same arc with different sweep angle
                    drawArc(
                        topLeft = topCenterOffset,
                        color = Color.Green,
                        useCenter = true,
                        startAngle = startAngle,
                        sweepAngle = -30f,
                        size = rectangleSize,
                        style = Stroke()
                    )
                    // same arc with different sweep angle
                    drawArc(
                        topLeft = topCenterOffset,
                        color = Color.Red,
                        useCenter = true,
                        startAngle = startAngle,
                        sweepAngle = -90f,
                        size = rectangleSize,
                        style = Stroke()
                    )
                    // same arc with different sweep angle
                    drawArc(
                        topLeft = topCenterOffset,
                        color = Color.Blue,
                        useCenter = true,
                        startAngle = startAngle,
                        sweepAngle = 120f,
                        size = rectangleSize,
                        style = Stroke()
                    )
                    // full circle
                    drawArc(
                        topLeft = topCenterOffset,
                        color = constructionColor,
                        useCenter = true,
                        startAngle = startAngle,
                        sweepAngle = 360f,
                        size = rectangleSize,
                        style = Stroke()
                    )
                    // example arc
                    drawArc(
                        topLeft = topCenterOffset,
                        color = NICE_COLOR,
                        useCenter = true,
                        startAngle = startAngle,
                        sweepAngle = 30f,
                        size = rectangleSize,
                        style = Fill,
                    )
                    // O°
                    drawCircle(
                        color = dotColor, center = Offset(
                            endOffset.x, (endOffset.y - topCenterOffset.y) * 0.75f
                        ), // why does this fraction work??
                        radius = dotRadius
                    )
                    drawCircle(
                        color = dotColor, center = Offset(
                            topCenterOffset.x, (endOffset.y - topCenterOffset.y) * 0.75f
                        ), // why does this fraction work??
                        radius = dotRadius
                    )
                    drawCircle(
                        color = dotColor, center = Offset(
                            (endOffset.x - topCenterOffset.x) * 0.85f, topCenterOffset.y
                        ), // why does this fraction work??
                        radius = dotRadius
                    )
                    drawCircle(
                        color = dotColor, center = Offset(
                            (endOffset.x - topCenterOffset.x) * 0.85f, endOffset.y
                        ), // why does this fraction work??
                        radius = dotRadius
                    )


                }) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "startAngle ${"%.0f".format(startAngle)}",
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,)
            }
        }
    }

    }
}

@Preview(showBackground = true)
@Composable
fun ShowArcToPreview() {
    ArcToSamplerTheme {
        ShowArcTo()
    }
}