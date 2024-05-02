package br.com.alertaseguro

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@Preview(showSystemUi = true)
@Composable
fun RegisterScreen(
    onSave: () -> Unit = {}
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onSave) {
                Icon(imageVector = Icons.Default.Done, contentDescription = null)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues)
                .scrollable(state = rememberScrollState(), orientation = Orientation.Vertical),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.woman),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )

            Text(text = "Registrar \nEnsira seus dados Pessoais")

            var nome by remember {
                mutableStateOf("")
            }

            var cpf by remember {
                mutableStateOf("")
            }

            var dataNascimento by remember { mutableStateOf(Calendar.getInstance().formattedDateTimeOffset.datetimeDbToDateExibition) }

            var openDialog by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                label = { Text("Nome Completo") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
                }
            )

            OutlinedTextField(
                value = cpf,
                onValueChange = { cpf = it },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                label = { Text("CPF") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.AccountBox, contentDescription = null)
                }
            )

            OutlinedTextField(
                value = "",
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { openDialog = true },
                label = { Text(text = "Data de Nascimento") },
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                }
            )

            OutlinedTextField(
                value = "",
                onValueChange = { v -> },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "E-mail") },
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = null)
                }
            )


            OutlinedTextField(
                value = "",
                onValueChange = { v -> },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Telefone") },
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = null)
                }
            )

            OutlinedTextField(
                value = "",
                onValueChange = { v -> },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Emergencia - 1") },
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = null)
                }
            )

            OutlinedTextField(
                value = "",
                onValueChange = { v -> },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Emergencia - 2") },
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = null)
                }
            )

            OutlinedTextField(
                value = "",
                onValueChange = { v -> },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Emergencia - 3") },
                shape = MaterialTheme.shapes.medium,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = null)
                }
            )

            if (openDialog) {
                RegisterBirthdateDialog(
                    onDismiss = { openDialog = false },
                    onDatePicked = { date -> dataNascimento = date })
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterBirthdateDialog(
    onDismiss: () -> Unit,
    onDatePicked: (String) -> Unit
) {
    val datePickerState = rememberDatePickerState()
    val confirmEnabled = remember { derivedStateOf { datePickerState.selectedDateMillis != null } }
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()

                    val currentDay = Calendar.getInstance().apply {
                        timeInMillis =
                            datePickerState.selectedDateMillis ?: Calendar.getInstance(
                                TimeZone.getTimeZone("UTC"),
                                Locale.getDefault()
                            ).timeInMillis
                        timeZone = TimeZone.getTimeZone("UTC")
                    }
                    onDatePicked(currentDay.apply {
                        set(Calendar.DAY_OF_MONTH, currentDay.get(Calendar.DAY_OF_MONTH))
                        set(Calendar.MONTH, currentDay.get(Calendar.MONTH))
                        set(Calendar.YEAR, currentDay.get(Calendar.YEAR))
                    }.formattedDateTimeOffset.datetimeDbToDateExibition)

                },
                enabled = confirmEnabled.value
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

val Calendar.formattedDateTimeOffset: String
    get() = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.getDefault()).format(this.time)

val String.datetimeDbToDateExibition: String
    get() {
        val date =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.getDefault()).parse(this)!!
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
    }