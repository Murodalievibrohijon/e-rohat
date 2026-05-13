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
import androidx.compose.material.icons.outlined.*
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
            
            // Настройки всех данных (Чётко как в оригинале)
            var docNum by remember { mutableStateOf("1351388") }
            var company by remember { mutableStateOf("ЧДММ Олуча авто") }
            var address by remember { mutableStateOf("шахри Душанбе нохияи Фирдавси") }
            var plate by remember { mutableStateOf("7079TT10") }
            var raqTab by remember { mutableStateOf("7079") }
            var driver by remember { mutableStateOf("Ятимов Умед Пирович") }
            var ownership by remember { mutableStateOf("Шахси") }
            var speedo by remember { mutableStateOf("150") }
            var outTime by remember { mutableStateOf("08:36") }

            when (screen) {
                "settings" -> SettingsScreen(
                    docNum, company, address, plate, raqTab, driver, ownership, speedo, outTime,
                    onSave = { d, c, a, p, r, dr, o, s, ot ->
                        docNum = d; company = c; address = a; plate = p; raqTab = r; driver = dr; ownership = o; speedo = s; outTime = ot
                        screen = "main"
                    },
                    onBack = { screen = "main" }
                )
                "main" -> Scaffold(
                    bottomBar = { CustomBottomBar() }
                ) { p ->
                    Box(Modifier.padding(p).fillMaxSize()) {
                        MainGridScreen(onOpen = { screen = "doc" }, onSettings = { screen = "settings" })
                    }
                }
                "doc" -> DocumentScreen(docNum, company, address, plate, raqTab, driver, ownership, speedo, outTime) { screen = "main" }
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

        // Центрируем блок с иконками ровно по середине экрана
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Роҳхатҳо", fontSize = 16.sp, color = Color.Black, modifier = Modifier.padding(bottom = 24.dp))
                
                val menu = listOf(
                    Triple("Автобус", Icons.Default.DirectionsBus, "bus"),
                    Triple("Троллейбус", Icons.Default.ElectricBus, "trolley"),
                    Triple("Микроавтобус", Icons.Default.DepartureBoard, "micro"),
                    Triple("Сабукрав", Icons.Default.DirectionsCar, "car"),
                    Triple("Шакли 26", Icons.Default.LocalFireDepartment, "truck"),
                    Triple("Шакли 566м", Icons.Default.Handyman, "truck2")
                )

                Column(Modifier.padding(horizontal = 10.dp)) {
                    menu.chunked(3).forEach { row ->
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            row.forEach { (name, icon, _) ->
                                Column(
                                    Modifier.weight(1f).padding(6.dp).clickable { if(name == "Сабукрав") onOpen() },
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(Modifier.size(65.dp).background(Color(0xFFF7F8F9), RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = icon,
                                            contentDescription = null,
                                            tint = Color(0xFF4CAF50),
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }
                                    Spacer(Modifier.height(4.dp))
                                    Text(name, fontSize = 12.sp, color = Color.DarkGray, textAlign = TextAlign.Center)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DocumentScreen(d: String, c: String, a: String, p: String, r: String, dr: String, o: String, s: String, ot: String, onBack: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize().background(Color.White).verticalScroll(rememberScrollState())) {
            Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null, tint = Color.Gray) }
                Text("Роҳхат", fontSize = 18.sp, modifier = Modifier.weight(1f), color = Color.Gray)
                Icon(Icons.Default.Description, null, tint = Color.Gray)
            }
            
            Text(
                "РОҲХАТИ АВТОМОБИЛИ САБУКРАВ № $d\nаз «25» Апрели с.2026 то «24» Майи с.2026",
                Modifier.padding(16.dp).fillMaxWidth(), fontWeight = FontWeight.Bold, fontSize = 14.sp, textAlign = TextAlign.Center, color = Color.Black
            )

            Column(Modifier.padding(horizontal = 8.dp).border(0.5.dp, Color.LightGray)) {
                val data = listOf(
                    "Ҳолати саломатии ронанда" to "Саломат",
                    "Ҳолати техникии автомобил" to "Коршоям",
                    "Нишондоди суръатнигор" to s,
                    "Рамзи роххат" to d,
                    "Корхона" to c,
                    "Суроғаи корхона" to a,
                    "Шакли моликият" to o,
                    "Рақ. таб." to r,
                    "Рақами давлатии автомобил" to p,
                    "Тамғаи автомобил" to "JAC",
                    "Ном ва насаби ронанда" to dr
                )
                data.forEach { (k, v) ->
                    Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min).border(0.2.dp, Color.LightGray)) {
                        Text(k, Modifier.weight(1f).padding(8.dp), fontSize = 12.sp, color = Color.Gray)
                        Box(Modifier.width(0.5.dp).fillMaxHeight().background(Color.LightGray))
                        Text(v, Modifier.weight(1.1f).padding(8.dp), fontSize = 12.sp, color = Color.Black)
                    }
                }
                
                Box(Modifier.fillMaxWidth().background(Color(0xFFF9F9F9)).padding(6.dp)) {
                    Text("Рӯзҳои кори", Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
                
                val workData = listOf("№" to "1", "Сана" to "2026-04-25", "Баромад" to ot, "Даромад" to "", "Суръатнигор баромад" to s)
                workData.forEach { (k, v) ->
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
fun SettingsScreen(d: String, c: String, a: String, p: String, r: String, dr: String, o: String, s: String, ot: String, onSave: (String, String, String, String, String, String, String, String, String) -> Unit, onBack: () -> Unit) {
    var td by remember { mutableStateOf(d) }; var tc by remember { mutableStateOf(c) }
    var ta by remember { mutableStateOf(a) }; var tp by remember { mutableStateOf(p) }
    var tr by remember { mutableStateOf(r) }; var tdr by remember { mutableStateOf(dr) }
    var tow by remember { mutableStateOf(o) }; var ts by remember { mutableStateOf(s) }; var tot by remember { mutableStateOf(ot) }

    Column(Modifier.fillMaxSize().background(Color.White).padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("Танзимоти пурра", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(value = td, onValueChange = { td = it }, label = { Text("Рақами роҳхат") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = tc, onValueChange = { tc = it }, label = { Text("Корхона") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = ta, onValueChange = { ta = it }, label = { Text("Суроғаи корхона") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = tp, onValueChange = { tp = it }, label = { Text("Рақами мошин") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = tdr, onValueChange = { tdr = it }, label = { Text("Номи ронанда") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = tow, onValueChange = { tow = it }, label = { Text("Шакли моликият") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = ts, onValueChange = { ts = it }, label = { Text("Суръатнигор") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = tot, onValueChange = { tot = it }, label = { Text("Баромад") }, modifier = Modifier.fillMaxWidth())
        Button(onClick = { onSave(td, tc, ta, tp, tr, tdr, tow, ts, tot) }, Modifier.fillMaxWidth().padding(top = 20.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))) { Text("САБТ КАРДАН") }
        TextButton(onClick = onBack, Modifier.fillMaxWidth()) { Text("Назад", color = Color.Gray) }
    }
}

@Composable
fun CustomBottomBar() {
    Column {
        Box(Modifier.fillMaxWidth().height(0.5.dp).background(Color.LightGray))
        Row(Modifier.fillMaxWidth().background(Color.White).padding(8.dp), Arrangement.SpaceAround) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) { Icon(Icons.Outlined.Home, null, tint = Color(0xFF4CAF50)); Text("Асосӣ", 10.sp, color = Color(0xFF4CAF50)) }
            Column(horizontalAlignment = Alignment.CenterHorizontally) { Icon(Icons.Outlined.QrCodeScanner, null, tint = Color.Gray); Text("QR-и ман", 10.sp, color = Color.Gray) }
            Column(horizontalAlignment = Alignment.CenterHorizontally) { Icon(Icons.Outlined.Article, null, tint = Color.Gray); Text("Шаҳодатнома", 10.sp, color = Color.Gray) }
        }
    }
}
