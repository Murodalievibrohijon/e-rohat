package com.e_rohat.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var screen by remember { mutableStateOf("main") }
            
            // Твои актуальные данные по умолчанию
            var docNum by remember { mutableStateOf("1351388") }
            var company by remember { mutableStateOf("ЧДММ Олуча авто") }
            var plate by remember { mutableStateOf("7079TT10") }
            var driver by remember { mutableStateOf("Ятимов Умед Пирович") }

            when (screen) {
                "settings" -> SettingsScreen(
                    docNum, company, plate, driver,
                    onSave = { d, c, p, n ->
                        docNum = d; company = c; plate = p; driver = n
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
                "doc" -> DocumentScreen(docNum, company, plate, driver) { screen = "main" }
            }
        }
    }
}

@Composable
fun MainGridScreen(onOpen: () -> Unit, onSettings: () -> Unit) {
    Column(Modifier.fillMaxSize().background(Color.White)) {
        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("Роҳхат ва борхати электронӣ", fontSize = 17.sp, color = Color.Gray, modifier = Modifier.weight(1f))
            IconButton(onClick = onSettings) { Icon(Icons.Default.Person, null, tint = Color.LightGray) }
        }
        Spacer(Modifier.height(100.dp))
        Text("Роҳхатҳо", Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 16.sp, color = Color.Black)
        Spacer(Modifier.height(20.dp))

        val menu = listOf("Автобус", "Троллейбус", "Микроавтобус", "Сабукрав", "Шакли 26", "Шакли 566м")
        Column(Modifier.padding(horizontal = 15.dp)) {
            menu.chunked(3).forEach { row ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    row.forEach { name ->
                        Column(
                            Modifier.weight(1f).padding(4.dp).clickable { if(name == "Сабукрав") onOpen() },
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(Modifier.size(60.dp).background(Color(0xFFF7F8F9), RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = when {
                                        name.contains("Автобус") -> Icons.Default.DirectionsBus
                                        name == "Сабукрав" -> Icons.Default.DirectionsCar
                                        else -> Icons.Default.LocalShipping
                                    },
                                    contentDescription = null,
                                    tint = Color(0xFF4CAF50),
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            Spacer(Modifier.height(4.dp))
                            Text(name, fontSize = 12.sp, color = Color.DarkGray)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DocumentScreen(d: String, c: String, p: String, n: String, onBack: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().background(Color.White).verticalScroll(rememberScrollState())) {
            Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null, tint = Color.Gray) }
                Text("Роҳхат", fontSize = 18.sp, modifier = Modifier.weight(1f), color = Color.DarkGray)
                Icon(Icons.Default.Description, null, tint = Color.Gray)
            }
            
            Text("РОҲХАТИ АВТОМОБИЛИ САБУКРАВ № $d\nаз «25» Апрели с.2026 то «24» Майи с.2026", Modifier.padding(16.dp), fontWeight = FontWeight.Bold, fontSize = 14.sp, textAlign = TextAlign.Center)

            Column(Modifier.padding(horizontal = 8.dp).border(0.5.dp, Color.LightGray)) {
                val data = listOf("Ҳолати саломатӣ" to "Саломат", "Ҳолати техникӣ" to "Коршоям", "Рамзи роххат" to d, "Корхона" to c, "Рақами мошин" to p, "Тамға" to "JAC", "Ронанда" to n)
                data.forEach { (k, v) ->
                    Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min).border(0.2.dp, Color.LightGray)) {
                        Text(k, Modifier.weight(1f).padding(8.dp), fontSize = 12.sp, color = Color.Gray)
                        Box(Modifier.width(0.5.dp).fillMaxHeight().background(Color.LightGray))
                        Text(v, Modifier.weight(1.1f).padding(8.dp), fontSize = 12.sp, color = Color.Black)
                    }
                }
            }
            Spacer(Modifier.height(80.dp))
        }
        FloatingActionButton(onClick = {}, modifier = Modifier.align(Alignment.BottomEnd).padding(20.dp), containerColor = Color(0xFF4CAF50), shape = CircleShape) {
            Icon(Icons.Default.Refresh, null, tint = Color.White)
        }
    }
}

@Composable
fun SettingsScreen(d: String, c: String, p: String, n: String, onSave: (String, String, String, String) -> Unit, onBack: () -> Unit) {
    var td by remember { mutableStateOf(d) }; var tc by remember { mutableStateOf(c) }
    var tp by remember { mutableStateOf(p) }; var tn by remember { mutableStateOf(n) }
    Column(Modifier.fillMaxSize().background(Color.White).padding(16.dp)) {
        Text("Танзимот", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        OutlinedTextField(value = td, onValueChange = { td = it }, label = { Text("Рақами роҳхат") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = tc, onValueChange = { tc = it }, label = { Text("Корхона") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = tp, onValueChange = { tp = it }, label = { Text("Рақами мошин") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = tn, onValueChange = { tn = it }, label = { Text("Ими ронанда") }, modifier = Modifier.fillMaxWidth())
        Button(onClick = { onSave(td, tc, tp, tn) }, Modifier.fillMaxWidth().padding(top = 20.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))) { Text("САБТ") }
    }
}

@Composable
fun CustomBottomBar() {
    Row(Modifier.fillMaxWidth().background(Color.White).padding(8.dp), Arrangement.SpaceAround) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) { Icon(Icons.Default.Home, null, tint = Color(0xFF4CAF50)); Text("Асосӣ", 10.sp, color = Color(0xFF4CAF50)) }
        Column(horizontalAlignment = Alignment.CenterHorizontally) { Icon(Icons.Default.QrCode, null, tint = Color.Gray); Text("QR-и ман", 10.sp) }
        Column(horizontalAlignment = Alignment.CenterHorizontally) { Icon(Icons.Default.Description, null, tint = Color.Gray); Text("Шаҳодатнома", 10.sp) }
    }
}
