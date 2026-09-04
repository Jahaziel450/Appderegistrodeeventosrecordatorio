package com.example.appderegistrodeeventosrecordatorio.ui.pantallas

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appderegistrodeeventosrecordatorio.datos.modelo.Prioridad
import com.example.appderegistrodeeventosrecordatorio.datos.modelo.Recordatorio
import com.example.appderegistrodeeventosrecordatorio.ui.theme.MoradoPrimario
import com.example.appderegistrodeeventosrecordatorio.ui.vista_modelo.RecordatorioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(
    vistaModelo: RecordatorioViewModel,
    esTemaOscuro: Boolean,
    alCambiarTema: () -> Unit,
    alAgregar: () -> Unit
) {
    val recordatorios by vistaModelo.recordatorios.collectAsState()
    var recordatorioParaEliminar by remember { mutableStateOf<Recordatorio?>(null) }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = alAgregar,
                containerColor = MoradoPrimario,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "EQUIPO 4",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MoradoPrimario
            )
            Text(
                text = "Mis recordatorios",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${recordatorios.size} recordatorios",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(recordatorios, key = { it.id }) { recordatorio ->
                    TarjetaRecordatorio(
                        recordatorio = recordatorio,
                        alEliminar = { recordatorioParaEliminar = recordatorio }
                    )
                }
            }
        }

        recordatorioParaEliminar?.let { recordatorio ->
            AlertDialog(
                onDismissRequest = { recordatorioParaEliminar = null },
                confirmButton = {
                    TextButton(
                        onClick = {
                            vistaModelo.eliminarRecordatorio(recordatorio.id)
                            recordatorioParaEliminar = null
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { recordatorioParaEliminar = null }) {
                        Text("Cancelar")
                    }
                },
                icon = { Icon(Icons.Default.Delete, contentDescription = null) },
                title = { Text("¿Eliminar recordatorio?") },
                text = { Text("Esta acción no se puede deshacer.") }
            )
        }
    }
}

@Composable
fun TarjetaRecordatorio(
    recordatorio: Recordatorio,
    alEliminar: () -> Unit
) {
    var expandido by remember { mutableStateOf(false) }
    val rotacion by animateFloatAsState(if (expandido) 180f else 0f)

    val colorPrioridad = when (recordatorio.prioridad) {
        Prioridad.ALTA -> Color(0xFFEF4444)
        Prioridad.MEDIA -> Color(0xFFF59E0B)
        Prioridad.BAJA -> Color(0xFF10B981)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expandido = !expandido },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = recordatorio.titulo,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.CalendarToday,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = recordatorio.fecha,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            color = colorPrioridad.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = recordatorio.prioridad.nombre,
                                color = colorPrioridad,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.rotate(rotacion)
                )
            }

            AnimatedVisibility(visible = expandido) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = recordatorio.descripcion,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { alEliminar() },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Eliminar",
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
        // Indicador de prioridad en el borde inferior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(colorPrioridad)
        )
    }
}
