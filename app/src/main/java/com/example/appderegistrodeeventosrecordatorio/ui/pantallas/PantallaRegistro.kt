package com.example.appderegistrodeeventosrecordatorio.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appderegistrodeeventosrecordatorio.datos.modelo.Prioridad
import com.example.appderegistrodeeventosrecordatorio.datos.modelo.Recordatorio
import com.example.appderegistrodeeventosrecordatorio.ui.theme.MoradoPrimario
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaRegistro(
    esTemaOscuro: Boolean,
    alCambiarTema: () -> Unit,
    alGuardar: (Recordatorio) -> Unit,
    alRegresar: () -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var fechaSeleccionada by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var mostrarDatePicker by remember { mutableStateOf(false) }
    var prioridad by remember { mutableStateOf(Prioridad.MEDIA) }
    var descripcion by remember { mutableStateOf("") }

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = fechaSeleccionada)
    val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = alRegresar,
                    modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Crear recordatorio",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Surface(
                        color = MoradoPrimario.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "Completa todos los campos",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MoradoPrimario,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
                
                // Botón de cambio de tema
                IconButton(
                    onClick = alCambiarTema,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface, CircleShape)
                        .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), CircleShape)
                ) {
                    Icon(
                        imageVector = if (esTemaOscuro) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = "Cambiar tema",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Título", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ej. Reunión importante", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    focusedBorderColor = MoradoPrimario,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text("Fecha", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            OutlinedTextField(
                value = formatoFecha.format(Date(fechaSeleccionada)),
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { mostrarDatePicker = true },
                enabled = false,
                readOnly = true,
                trailingIcon = {
                    Icon(Icons.Default.CalendarToday, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text("Prioridad", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BotonPrioridad(
                    texto = "Alta",
                    color = Color(0xFFEF4444),
                    seleccionado = prioridad == Prioridad.ALTA,
                    alSeleccionar = { prioridad = Prioridad.ALTA },
                    modifier = Modifier.weight(1f)
                )
                BotonPrioridad(
                    texto = "Media",
                    color = Color(0xFFF59E0B),
                    seleccionado = prioridad == Prioridad.MEDIA,
                    alSeleccionar = { prioridad = Prioridad.MEDIA },
                    modifier = Modifier.weight(1f)
                )
                BotonPrioridad(
                    texto = "Baja",
                    color = Color(0xFF10B981),
                    seleccionado = prioridad == Prioridad.BAJA,
                    alSeleccionar = { prioridad = Prioridad.BAJA },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Descripción", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                placeholder = { Text("Detalla el evento o recordatorio...", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    focusedBorderColor = MoradoPrimario,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (titulo.isNotBlank()) {
                        alGuardar(
                            Recordatorio(
                                titulo = titulo,
                                fecha = formatoFecha.format(Date(fechaSeleccionada)),
                                prioridad = prioridad,
                                descripcion = descripcion
                            )
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MoradoPrimario),
                enabled = titulo.isNotBlank()
            ) {
                Text("Guardar recordatorio", color = Color.White)
            }
        }
    }

    if (mostrarDatePicker) {
        DatePickerDialog(
            onDismissRequest = { mostrarDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        fechaSeleccionada = it
                    }
                    mostrarDatePicker = false
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
fun BotonPrioridad(
    texto: String,
    color: Color,
    seleccionado: Boolean,
    alSeleccionar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(48.dp)
            .clickable { alSeleccionar() },
        shape = RoundedCornerShape(12.dp),
        color = if (seleccionado) color else Color.Transparent,
        border = if (seleccionado) null else androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.3f))
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = texto,
                color = if (seleccionado) Color.White else color,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
