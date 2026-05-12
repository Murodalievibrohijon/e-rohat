package com.erohat.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class RohatDigital(
    val statusHealth: String = "Саломат",
    val statusTech: String = "Коршоям",
    val odometer: String = "1250",
    val company: String = "Эко Такси",
    val plateNumber: String = "7062TT10",
    val carModel: String = "JAC iEVS4",
    val driver: String = "Муродалиев Иброҳим"
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { App() }
    }
}

@Composable
fun App() {
    var screen by remember { mutableStateOf("menu") }
    var data by remember { mutableStateOf(RohatDigital()) }
    var showSettings by remember { mutableStateOf(false) }

    when (screen) {
        "menu" -> MainMenu(open = { screen = "form" }, settings = { showSettings = true })
        "form" -> FormScreen(data = data, back = { screen = "menu" }, settings = { showSettings = true })
    }
    if (showSettings) {
        SettingsScreen(data = data, onSave = { data = it }, onClose = { showSettings = false })
    }
}

@Composable
fun MainMenu(open: () -> Unit, settings: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("e-роҳхат", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(20.dp))
        Button(onClick = open) { Text("Сабукрав") }
        Spacer(Modifier.height(10.dp))
        Button(onClick = settings) { Text("Настройки") }
    }
}

@Composable
fun FormScreen(data: RohatDigital, back: () -> Unit, settings: () -> Unit) {
    Column(Modifier.padding(16.dp)) {
        Text("РОҲХАТИ САБУКРАВ", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(10.dp))
        Text("Ронанда: ${data.driver}")
        Text("Мошина: ${data.carModel}")
        Text("Номер: ${data.plateNumber}")
        Text("Корхона: ${data.company}")
        Text("Одометр: ${data.odometer}")
        Text("Саломат: ${data.statusHealth}")
        Text("Тех: ${data.statusTech}")
        Spacer(Modifier.height(20.dp))
        Button(onClick = settings) { Text("Редактировать") }
        Spacer(Modifier.height(10.dp))
        Button(onClick = back) { Text("Назад") }
    }
}

@Composable
fun SettingsScreen(data: RohatDigital, onSave: (RohatDigital) -> Unit, onClose: () -> Unit) {
    var password by remember { mutableStateOf("") }
    var temp by remember { mutableStateOf(data) }
    AlertDialog(onDismissRequest = onClose, confirmButton = {}, text = {
        Column {
            if (password != "1234") {
                Text("Введите пароль")
                OutlinedTextField(value = password, onValueChange = { password = it })
            } else {
                OutlinedTextField(temp.driver, { temp = temp.copy(driver = it) }, label = { Text("Ронанда") })
                OutlinedTextField(temp.carModel, { temp = temp.copy(carModel = it) }, label = { Text("Мошина") })
                OutlinedTextField(temp.plateNumber, { temp = temp.copy(plateNumber = it) }, label = { Text("Номер") })
                OutlinedTextField(temp.company, { temp = temp.copy(company = it) }, label = { Text("Корхона") })
                OutlinedTextField(temp.odometer, { temp = temp.copy(odometer = it) }, label = { Text("Одометр") })
                Spacer(Modifier.height(10.dp))
                Button(onClick = { onSave(temp); onClose() }) { Text("Сохранить") }
            }
        }
    })
}
