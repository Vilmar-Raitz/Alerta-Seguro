package br.com.alertaseguro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alertaseguro.ui.theme.AlertaSeguroTheme
import br.com.alertaseguro.ui.theme.red

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

@Composable
fun HomeScreen() {
    Column(verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally
        , modifier = Modifier.fillMaxSize()) {
        Text(
            text = buildAnnotatedString {
                append("Está em Perigo?")

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
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Black,
            lineHeight = 24.sp,
            textAlign = TextAlign.Center,
            color = Color.DarkGray
        )

        Surface(
            shape = CircleShape,
            color = red,
            modifier = Modifier.size(100.dp),
            border = BorderStroke(2.dp, Color.LightGray),

            ) {
            Box(contentAlignment = Alignment.Center) {
                Text(text = "SOS", fontWeight = FontWeight.Black, fontSize = 32.sp)
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