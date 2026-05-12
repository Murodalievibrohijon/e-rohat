package com.erohat.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
            Scaffold(
                bottomBar = { if (screen == "main") CustomBottomBar() }
            ) { p ->
                Box(Modifier.padding(p)) {
                    if (screen == "main") MainGridScreen { screen = "doc" }
                    else DocumentScreen { screen = "main" }
                }
            }
        }
    }
}

@Composable
fun MainGridScreen(onOpen: () -> Unit) {
    Column(Modifier.fillMaxSize().background(Color.White)) {
        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("Роҳхат ва борхати электронӣ", fontSize = 18.sp, color = Color.Gray, modifier = Modifier.weight(1f))
            Text("👤", fontSize = 24.sp)
        }
        Spacer(Modifier.height(80.dp))
        Text("Роҳхатҳо", Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 16.sp)
        Spacer(Modifier.height(20.dp))

        val menu = listOf("Автобус", "Троллейбус", "Микроавтобус", "Сабукрав", "Шакли 26", "Шакли 566м")
        Column(Modifier.padding(8.dp)) {
            menu.chunked(3).forEach { row ->
                Row(Modifier.fillMaxWidth()) {
                    row.forEach { name ->
                        Card(
                            modifier = Modifier.weight(1f).padding(4.dp).clickable { if(name == "Сабукрав") onOpen() },
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F8F9)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Column(Modifier.padding(12.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("🚗", fontSize = 28.sp)
                                Spacer(Modifier.height(8.dp))
                                Text(name, fontSize = 11.sp, color = Color.Black)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DocumentScreen(onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().background(Color.White).verticalScroll(rememberScrollState())) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = onBack) { Text("⬅ Назад", color = Color.Black) }
            Text("Роҳхат", fontSize = 20.sp, modifier = Modifier.weight(1f))
            Text("📄", fontSize = 24.sp)
        }

        Text(
            "РОҲХАТИ АВТОМОБИЛИ САБУКРАВ № 1351388\nаз «13» Майи с.2026 то «12» Июни с.2026",
            Modifier.padding(16.dp), fontWeight = FontWeight.Bold, fontSize = 14.sp
        )

        Column(Modifier.padding(horizontal = 12.dp).border(0.5.dp, Color.LightGray)) {
            val d = listOf(
                "Ҳолати саломатии ронанда" to "Саломат",
                "Ҳолати техникии автомобил" to "Коршоям",
                "Нишондоди суръатнигор" to "1250",
                "Рамзи роҳхат" to "1351388",
                "Корхона" to "Эко Такси",
                "Суроғаи корхона" to "шаҳри Душанбе ноҳияи Фирдавсӣ",
                "Шакли моликият" to "Шахсӣ",
                "Рақ. таб." to "7062",
                "Рақами давлатии автомобил" to "7062TT10",
                "Тамғаи автомобил" to "JAC iEVS4",
                "Ном ва насаби ронанда" to "Муродалиев Иброҳим"
            )
            d.forEach { (k, v) ->
                Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min).border(0.5.dp, Color.LightGray)) {
                    Text(k, Modifier.weight(1f).padding(10.dp), fontSize = 12.sp)
                    Box(Modifier.width(0.5.dp).fillMaxHeight().background(Color.LightGray))
                    Text(v, Modifier.weight(1f).padding(10.dp), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        Spacer(Modifier.height(50.dp))
    }
}

@Composable
fun CustomBottomBar() {
    Row(Modifier.fillMaxWidth().background(Color.White).padding(10.dp), horizontalArrangement = Arrangement.SpaceAround) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) { Text("🏠", fontSize = 20.sp); Text("Асосӣ", fontSize = 10.sp, color = Color(0xFF4CAF50)) }
        Column(horizontalAlignment = Alignment.CenterHorizontally) { Text("🔳", fontSize = 20.sp); Text("QR-и ман", fontSize = 10.sp) }
        Column(horizontalAlignment = Alignment.CenterHorizontally) { Text("📝", fontSize = 20.sp); Text("Шаҳодатнома", fontSize = 10.sp) }
    }
}
