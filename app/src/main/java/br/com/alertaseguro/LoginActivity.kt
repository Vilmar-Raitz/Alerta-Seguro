package br.com.alertaseguro

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alertaseguro.ui.theme.AlertaSeguroTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlertaSeguroTheme {
                LoginScreen() {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }

            }

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginScreen(onClick: () -> Unit = {}) {
    Column (modifier = Modifier
        .fillMaxSize()
        .padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "Login", Modifier.fillMaxWidth(), fontSize = 28.sp, fontWeight = FontWeight.Bold)

        var email by remember {
            mutableStateOf("")
        }
        var senha by remember {
            mutableStateOf("")
        }

        var senhaOn by remember{
            mutableStateOf(false)
        }

        TextField(value = email, onValueChange = {
                                                 email = it
        }, Modifier.fillMaxWidth(), label = {Text( "E-mail")})
        TextField(value = senha, onValueChange = {
                                                 senha = it
        }, Modifier.fillMaxWidth(), label = { Text(text = "Senha")}, visualTransformation = if (senhaOn) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), trailingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = null, modifier = Modifier.clickable { senhaOn = !senhaOn })
            }
        )

        Row {
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Esqueci Senha")
            }
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Fazer Cadastro")
            }
        }


        Button(onClick = onClick) {
            Text(text = "Logar")
        }
    }
}