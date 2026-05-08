package com.kreedaprerana.scout.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrialLoggerScreen(onBack: () -> Unit) {
    var selectedAthlete by remember { mutableStateOf("Select Athlete") }
    var selectedTrialType by remember { mutableStateOf("Select Trial Type") }
    var distance by remember { mutableStateOf("") }
    
    var timeMillis by remember { mutableLongStateOf(0L) }
    var isRunning by remember { mutableStateOf(false) }

    var athleteExpanded by remember { mutableStateOf(false) }
    var typeExpanded by remember { mutableStateOf(false) }

    val athletes = listOf("Rahul Kumar", "Sneha Patil", "Amit Singh", "Priya Das")
    val trialTypes = listOf("Sprint", "Long Jump", "High Jump", "Shot Put")

    LaunchedEffect(isRunning) {
        if (isRunning) {
            val startTime = System.currentTimeMillis() - timeMillis
            while (isRunning) {
                timeMillis = System.currentTimeMillis() - startTime
                delay(10)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Log Trial", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Athlete Selection
            ExposedDropdownMenuBox(
                expanded = athleteExpanded,
                onExpandedChange = { athleteExpanded = !athleteExpanded }
            ) {
                OutlinedTextField(
                    value = selectedAthlete,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Athlete") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = athleteExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                ExposedDropdownMenu(
                    expanded = athleteExpanded,
                    onDismissRequest = { athleteExpanded = false }
                ) {
                    athletes.forEach { name ->
                        DropdownMenuItem(
                            text = { Text(name) },
                            onClick = {
                                selectedAthlete = name
                                athleteExpanded = false
                            }
                        )
                    }
                }
            }

            // Trial Type Selection
            ExposedDropdownMenuBox(
                expanded = typeExpanded,
                onExpandedChange = { typeExpanded = !typeExpanded }
            ) {
                OutlinedTextField(
                    value = selectedTrialType,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Trial Type") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = typeExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                ExposedDropdownMenu(
                    expanded = typeExpanded,
                    onDismissRequest = { typeExpanded = false }
                ) {
                    trialTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type) },
                            onClick = {
                                selectedTrialType = type
                                typeExpanded = false
                            }
                        )
                    }
                }
            }

            if (selectedTrialType == "Sprint") {
                Spacer(modifier = Modifier.height(16.dp))

                // Distance Input
                OutlinedTextField(
                    value = distance,
                    onValueChange = { distance = it },
                    label = { Text("Distance (Meters)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Timer Display
                Text(
                    text = formatTime(timeMillis),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 72.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Timer Controls
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (!isRunning) {
                        Button(
                            onClick = { isRunning = true },
                            modifier = Modifier.weight(1f).height(72.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "Start")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("START", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Button(
                            onClick = { isRunning = false },
                            modifier = Modifier.weight(1f).height(72.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Icon(Icons.Default.Stop, contentDescription = "Stop")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("STOP", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    OutlinedButton(
                        onClick = {
                            isRunning = false
                            timeMillis = 0L
                        },
                        modifier = Modifier.height(72.dp).width(100.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Reset")
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Save Button
                Button(
                    onClick = { /* Save Trial Logic */ },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !isRunning && timeMillis > 0
                ) {
                    Text("Save Trial Result", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

private fun formatTime(millis: Long): String {
    val seconds = (millis / 1000) % 60
    val minutes = (millis / (1000 * 60)) % 60
    val hundreds = (millis / 10) % 100
    return String.format("%02d:%02d.%02d", minutes, seconds, hundreds)
}
