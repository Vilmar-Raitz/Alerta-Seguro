package br.com.alertaseguro

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun RegisterScreen() {
    Column {
        Image(painter = painterResource(id = R.drawable.woman), contentDescription = null)

        Text(text = "Registrar \nEnsira seus dados Pessoais")

        var nome by remember {
            mutableStateOf("")
        }

        var cpf by remember {
            mutableStateOf("")
        }

        var dataNascimento by remember {
            mutableStateOf("")
        }

        var openDialog by remember { mutableStateOf(false) }


        TextField(value = nome, onValueChange = {
            nome = it
        }, Modifier.fillMaxWidth(), label = { Text("Nome Completo") })

        TextField(value = cpf, onValueChange = {
            cpf = it
        }, Modifier.fillMaxWidth(), label = { Text("CPF") })

        Button(onClick = { openDialog = true }) {

        }

        TextField(value = dataNascimento, onValueChange = {
        },
            Modifier
                .fillMaxWidth()
                .clickable { openDialog = true }, label = { Text("Data Nascimento") })

        if (openDialog) {
            val datePickerState = rememberDatePickerState()
            val confirmEnabled = remember {
                derivedStateOf { datePickerState.selectedDateMillis != null }
            }
            DatePickerDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onDismissRequest.
                    openDialog = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDialog = false

                            val currentDay = Calendar.getInstance().apply {
                                timeInMillis =
                                    datePickerState.selectedDateMillis ?: Calendar.getInstance(
                                        TimeZone.getTimeZone("UTC"),
                                        Locale.getDefault()
                                    ).timeInMillis
                                timeZone = TimeZone.getTimeZone("UTC")
                            }
                             dataNascimento = currentDay.apply {
                                set(Calendar.DAY_OF_MONTH, currentDay.get(Calendar.DAY_OF_MONTH))
                                set(Calendar.MONTH, currentDay.get(Calendar.MONTH))
                                set(Calendar.YEAR, currentDay.get(Calendar.YEAR))
                            } .formattedDateTimeOffset.datetimeDbToDateExibition

                        },
                        enabled = confirmEnabled.value
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDialog = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        
    }

}val Calendar.formattedDateTimeOffset: String
    get() = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.getDefault()).format(this.time)

val String.datetimeDbToDateExibition: String
    get() {
        val date =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.getDefault()).parse(this)!!
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
    }