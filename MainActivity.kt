package com.e_rohat.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var screen by remember { mutableStateOf("login") }
            
            // Все данные теперь здесь - их можно менять в приложении
            var docNum by remember { mutableStateOf("1351388") }
            var company by remember { mutableStateOf("Эко Такси") }
            var address by remember { mutableStateOf("шаҳри Душанбе ноҳияи Фирдавсӣ") }
            var carModel by remember { mutableStateOf("JAC iEVS4") }
            var carPlate by remember { mutableStateOf("7062TT10") }
            var driver by remember { mutableStateOf("Муродалиев Иброҳим") }

            when (screen) {
                "login" -> LoginScreen { screen = "main" }
                "settings" -> SettingsScreen(
                    docNum, company, address, carModel, carPlate, driver,
                    onSave = { d, c, a, m, p, n ->
                        docNum = d; company = c; address = a; carModel = m; carPlate = p; driver = n
                        screen = "main"
                    },
                    onBack = { screen = "main" }
                )
                "main" -> Scaffold(
                    bottomBar = { CustomBottomBar() }
                ) { p ->
                    Box(Modifier.padding(p)) {
                        MainGridScreen(onOpen = { screen = "doc" }, onSettings = { screen = "settings" })
                    }
                }
                "doc" -> DocumentScreen(docNum, company, address, carModel, carPlate, driver) { screen = "main" }
            }
        }
    }
}

@Composable
fun LoginScreen(onSuccess: () -> Unit) {
    var code by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().background(Color.White).padding(24.dp), Arrangement.Center, Alignment.CenterHorizontally) {
        Text("Системаи «e-Роҳхат»", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4CAF50))
        Spacer(Modifier.height(30.dp))
        OutlinedTextField(value = code, onValueChange = { if(it.length <= 4) code = it }, label = { Text("Рамз (0000)") }, visualTransformation = PasswordVisualTransformation())
        if (error) Text("Хато!", color = Color.Red)
        Spacer(Modifier.height(20.dp))
        Button(onClick = { if(code == "0000") onSuccess() else error = true }, Modifier.fillMaxWidth().height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))) { Text("ДАРОМАД") }
    }
}

@Composable
fun SettingsScreen(d: String, c: String, a: String, m: String, p: String, n: String, onSave: (String, String, String, String, String, String) -> Unit, onBack: () -> Unit) {
    var td by remember { mutableStateOf(d) }; var tc by remember { mutableStateOf(c) }
    var ta by remember { mutableStateOf(a) }; var tm by remember { mutableStateOf(m) }
    var tp by remember { mutableStateOf(p) }; var tn by remember { mutableStateOf(n) }

    Column(Modifier.fillMaxSize().background(Color.White).padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("Тағйири маълумот", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(15.dp))
        OutlinedTextField(value = td, onValueChange = { td = it }, label = { Text("Рақами роҳхат") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = tc, onValueChange = { tc = it }, label = { Text("Номи Корхона") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = ta, onValueChange = { ta = it }, label = { Text("Суроға") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = tm, onValueChange = { tm = it }, label = { Text("Тамғаи мошин") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = tp, onValueChange = { tp = it }, label = { Text("Рақами давлатӣ") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = tn, onValueChange = { tn = it }, label = { Text("Номи ронанда") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(20.dp))
        Button(onClick = { onSave(td, tc, ta, tm, tp, tn) }, Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))) { Text("САБТ") }
        TextButton(onClick = onBack, Modifier.fillMaxWidth()) { Text("БА ПАҚ") }
    }
}

@Composable
fun MainGridScreen(onOpen: () -> Unit, onSettings: () -> Unit) {
    Column(Modifier.fillMaxSize().background(Color.White)) {
        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("Интихоби нақлиёт", Modifier.weight(1f), fontSize = 18.sp, color = Color.Gray)
            IconButton(onClick = onSettings) { Icon(Icons.Default.Settings, null, tint = Color.Gray) }
        }
        val menu = listOf("Автобус", "Троллейбус", "Микроавтобус", "Сабукрав", "Шакли 26", "Шакли 566м")
        Column(Modifier.padding(8.dp)) {
            menu.chunked(3).forEach { row ->
                Row(Modifier.fillMaxWidth()) {
                    row.forEach { name ->
                        Card(Modifier.weight(1f).padding(4.dp).clickable { if(name == "Сабукрав") onOpen() }, colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F8F9))) {
                            Column(Modifier.padding(15.dp).fillMaxWidth(), Alignment.CenterHorizontally) {
                                Text(if(name.contains("бус")) "🚌" else "🚗", fontSize = 30.sp)
                                Text(name, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DocumentScreen(d: String, c: String, a: String, m: String, p: String, n: String, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().background(Color.White).verticalScroll(rememberScrollState())) {
        TextButton(onClick = onBack, Modifier.padding(10.dp)) { Text("⬅ БОЗГАШТ", color = Color.Black) }
        Text("РОҲХАТИ № $d", Modifier.padding(16.dp), fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Column(Modifier.padding(12.dp).border(0.5.dp, Color.LightGray)) {
            val data = listOf("Корхона" to c, "Суроға" to a, "Мошин" to m, "Рақам" to p, "Ронанда" to n, "Ҳолат" to "Коршоям")
            data.forEach { (k, v) ->
                Row(Modifier.fillMaxWidth().border(0.5.dp, Color.LightGray)) {
                    Text(k, Modifier.weight(1f).padding(10.dp), fontSize = 12.sp)
                    Text(v, Modifier.weight(1.5f).padding(10.dp), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun CustomBottomBar() {
    Row(Modifier.fillMaxWidth().background(Color.White).padding(10.dp), Arrangement.SpaceAround) {
        Column(Alignment.CenterHorizontally) { Text("🏠", fontSize = 20.sp); Text("Асосӣ", fontSize = 10.sp, color = Color(0xFF4CAF50)) }
        Column(Alignment.CenterHorizontally) { Text("🔳", fontSize = 20.sp); Text("QR-код", fontSize = 10.sp) }
    }
}
