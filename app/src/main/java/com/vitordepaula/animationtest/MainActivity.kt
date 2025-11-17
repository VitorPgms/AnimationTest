package com.vitordepaula.animationtest

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vitordepaula.animationtest.ui.theme.AnimationTestTheme
import com.vitordepaula.animationtest.ui.theme.PurpleGrey40

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AnimationTestTheme {

                var tabPage by remember { mutableStateOf(TabPage.Home) }

                Scaffold(
                    bottomBar = {
                        SootheBottomNavigation(
                            tabPage = tabPage,
                            onTabSelected = { tabPage = it }
                        )
                    }
                ) {
                    ScreenProfile(
                        modifier = Modifier.padding(top = it.calculateTopPadding())
                    )
                }
            }
        }
    }
}



@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedProfileBorder(
            imageRes = R.drawable.image_perfil
        )
        Text(
            text = stringResource(R.string.text_profile),
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier
                .padding(top = 8.dp)
        )
        Text(
            text = stringResource(R.string.text_position),
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray,
            modifier = modifier
                .padding(8.dp)
        )
    }
}

@Composable
fun ProfileDescription(
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
                modifier = modifier
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun ScreenProfile(
    modifier: Modifier = Modifier
        .padding(top = 30.dp)
) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ProfileHeader()
        Spacer(modifier = Modifier.padding(top = 24.dp))
        ProfileDescription(
            stringResource(R.string.text_description)
        )
    }
}

enum class TabPage { Home, Profile}

@Composable
fun SootheBottomNavigation(
    tabPage: TabPage,
    onTabSelected: (TabPage) -> Unit,
    modifier: Modifier = Modifier
) {

    val backgroundColor by animateColorAsState(
        targetValue = if (tabPage == TabPage.Home) Color(0xFFFFF5EE) else Color(0xFF90EE90),
        label = "backgorund color"
    )

    NavigationBar(
        containerColor = backgroundColor,
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.botton_navigation_home)
                )
            },
            selected = tabPage == TabPage.Home,
            onClick = {onTabSelected(TabPage.Home)}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.botton_navigation_profile)
                )
            },
            selected = tabPage == TabPage.Profile,
            onClick = {onTabSelected(TabPage.Profile)}
        )
    }

}

@Composable
fun AnimatedProfileBorder(
    imageRes: Int,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing)
        )
    )

    Box(
        modifier = modifier
            .size(200.dp) // quase do tamanho da imagem
            .drawBehind {
                rotate(rotation) {
                    drawCircle(
                        brush = Brush.sweepGradient(
                            listOf(
                                Color.Red,
                                Color.White,
                                Color.Blue,
                                Color.Black,
                                Color.Red
                            )
                        ),
                        style = Stroke(width = 12.dp.toPx()) // borda fina
                    )
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp) // apenas um pouco menor
                .clip(CircleShape)
        )
    }
}



@Composable
fun Greeting() {
    Text(
        text = "Hello"
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnimationTestTheme {

        var tabPage by remember { mutableStateOf(TabPage.Home) }

        Scaffold(
            bottomBar = {
                SootheBottomNavigation(
                    tabPage,
                 { tabPage = it }
            )}
        ) {
            ScreenProfile()
        }
    }
}