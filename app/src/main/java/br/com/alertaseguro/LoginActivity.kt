package br.com.alertaseguro

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.alertaseguro.ui.theme.AlertaSeguroTheme

class LoginActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlertaSeguroTheme {
                val navController = rememberNavController()

                Scaffold(topBar = {
                    CenterAlignedTopAppBar(title = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_alerta_seguro),
                            contentDescription = null
                        )
                    }
                    )
                }) { paddingValues ->
                    LoginNavHost(
                        modifier = Modifier.padding(paddingValues),
                        navController = navController,
                        onLogin = {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLogin: () -> Unit = {},
    onRegister: () -> Unit = {},
    onForgot: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Login",
            Modifier.fillMaxWidth(),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        var email by remember {
            mutableStateOf("")
        }
        var senha by remember {
            mutableStateOf("")
        }

        var senhaOn by remember {
            mutableStateOf(false)
        }

        OutlinedTextField(
            value = email, onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(), label = { Text("E-mail") },
            shape = MaterialTheme.shapes.medium
        )
        OutlinedTextField(value = senha,
            onValueChange = { senha = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Senha") },
            shape = MaterialTheme.shapes.medium,
            visualTransformation = if (senhaOn) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    modifier = Modifier.clickable { senhaOn = !senhaOn })
            }
        )

        Row {
            TextButton(onClick = onForgot) {
                Text(text = "Esqueci Senha")
            }
            TextButton(onClick = onRegister) {
                Text(text = "Fazer Cadastro")
            }
        }

        Button(onClick = onLogin) {
            Text(text = "Logar")
        }
    }
}

@Composable
private fun LoginNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onLogin: () -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier.padding()
    ) {
        composable(route = "login") {
            BackHandler(enabled = false) {}
            LoginScreen(
                onLogin = { onLogin() },
                onRegister = {
                    navController.navigate("registerScreen") {
                        launchSingleTop = true
                    }
                },
                onForgot = {
                    navController.navigate("forgotScreen") {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = "registerScreen") {
            RegisterScreen {
                navController.navigate("login") {
                    launchSingleTop = true
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                }
            }
        }

        composable(route = "forgotScreen") {
            Text(text = "Esqueci a senha")
        }

    }
}