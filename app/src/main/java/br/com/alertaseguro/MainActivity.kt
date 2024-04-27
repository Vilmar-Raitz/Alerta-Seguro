package br.com.alertaseguro

import android.content.res.Resources
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.EaseInOutExpo
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alertaseguro.ui.theme.AlertaSeguroTheme
import br.com.alertaseguro.ui.theme.green
import br.com.alertaseguro.ui.theme.red
import br.com.alertaseguro.ui.theme.yellow
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlertaSeguroTheme {
                HomeScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Image(
                painter = painterResource(id = R.drawable.ic_alerta_seguro),
                contentDescription = null
            )
        },
            navigationIcon = { Icon(imageVector = Icons.Default.Menu, contentDescription = null) },
            actions = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            })
    }, bottomBar = {
        var state by remember { mutableStateOf(0) }
        Column {
            TabRow(selectedTabIndex = state) {
                Navigation.entries.forEachIndexed { index, title ->
                    Tab(
                        selected = state == index,
                        onClick = { state = index },
                        text = {
                            Text(text = title.name)
                        }, icon = {
                            Icon(imageVector = title.icon, contentDescription = null)
                        }
                    )
                }
            }

        }
    }) {

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(it)
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Está em Perigo?")
                    appendLine()

                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp, color = Color.Gray
                        )
                    ) {
                        append(" Clique no botão abaixo para disparar um alerta de perigo")
                    }

                },
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Black,
                lineHeight = 24.sp,
                textAlign = TextAlign.Center,
                color = Color.DarkGray
            )


            Box {
                val context = LocalContext.current

                val mPlayer: MediaPlayer = remember { MediaPlayer.create(context, R.raw.alarm) }

                var inAlert by remember { mutableStateOf(false) }
                var scale by remember { mutableFloatStateOf(1f) }
                val animatedScale by animateFloatAsState(
                    targetValue = scale,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = EaseInOutExpo),
                        repeatMode = RepeatMode.Reverse
                    )
                )

                LaunchedEffect(inAlert) {
                    if (inAlert) {
                        while (inAlert) {
                            scale = 2f
                            delay(1000)
                            scale = 1f
                            if (!mPlayer.isPlaying) {
                                mPlayer.start()
                            }
                        }
                    } else {
                        scale = 1f
                        delay(1000)
                        if (mPlayer.isPlaying) {
                            mPlayer.stop()
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .size((Resources.getSystem().displayMetrics.widthPixels / 4).dp)
                        .scale(if (inAlert) animatedScale else 1f)
                        .background((if (inAlert) red else green).copy(alpha = 0.3f), CircleShape)
                )
                Surface(
                    shape = CircleShape,
                    color = if (inAlert) red else MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size((Resources.getSystem().displayMetrics.widthPixels / 4).dp),
                    onClick = {
                        inAlert = !inAlert
                    }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "SOS",
                            fontWeight = FontWeight.Black,
                            fontSize = 52.sp,
                            color = MaterialTheme.colorScheme.onError
                        )
                    }
                }

                DisposableEffect(Unit) {
                    onDispose {
                        mPlayer.release()
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ElevatedCard(
                    modifier = Modifier.weight(1f),
                    onClick = {},
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = yellow,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "Acho que estou Sendo Seguido")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = null
                            )
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = null
                            )
                        }
                    }
                }
                ElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                    onClick = {}
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "Compartilhar Localização")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    AlertaSeguroTheme {
        HomeScreen()
    }

}

enum class Navigation(val icon: ImageVector) {
    Inicio(Icons.Default.Home),
    Mapa(Icons.Default.LocationOn)
}