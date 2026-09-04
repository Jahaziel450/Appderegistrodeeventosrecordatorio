package com.example.appderegistrodeeventosrecordatorio.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appderegistrodeeventosrecordatorio.ui.theme.MoradoPrimario

@Composable
fun PantallaAutenticacion(
    esTemaOscuro: Boolean,
    alCambiarTema: () -> Unit,
    alRegistrar: () -> Unit
) {
    var correo by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var aceptoTerminos by remember { mutableStateOf(false) }
    var mostrarContraseña by remember { mutableStateOf(false) }

    val correoValido = correo == "equipo4@tecmilenio.mx"
    val contraseñaValida = contraseña.length >= 8
    val formularioValido = correoValido && contraseñaValida && aceptoTerminos

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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icono y Título
            Surface(
                modifier = Modifier.size(80.dp),
                shape = RoundedCornerShape(16.dp),
                color = MoradoPrimario
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Equipo 4",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Tecmilenio · Recordatorios",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Campo Correo
            Text(
                text = "Correo electrónico",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            )
            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("equipo4@tecmilenio.mx", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)) },
                shape = RoundedCornerShape(12.dp),
                isError = correo.isNotEmpty() && !correoValido,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    focusedBorderColor = MoradoPrimario,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                )
            )
            if (correo.isNotEmpty() && !correoValido) {
                Text(
                    text = "El correo es requerido",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo Contraseña
            Text(
                text = "Contraseña",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            )
            OutlinedTextField(
                value = contraseña,
                onValueChange = { contraseña = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Mínimo 8 caracteres", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)) },
                shape = RoundedCornerShape(12.dp),
                isError = contraseña.isNotEmpty() && !contraseñaValida,
                visualTransformation = if (mostrarContraseña) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { mostrarContraseña = !mostrarContraseña }) {
                        Icon(
                            imageVector = if (mostrarContraseña) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                        )
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    focusedBorderColor = MoradoPrimario,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                )
            )
            if (contraseña.isNotEmpty() && !contraseñaValida) {
                Text(
                    text = "La contraseña debe tener al menos 8 caracteres",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Checkbox Términos Mejorado
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { aceptoTerminos = !aceptoTerminos },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .border(
                            width = 2.dp,
                            color = if (aceptoTerminos) MoradoPrimario else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                        .background(
                            color = if (aceptoTerminos) MoradoPrimario else Color.Transparent,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (aceptoTerminos) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                            append("Acepto los ")
                        }
                        withStyle(style = SpanStyle(color = MoradoPrimario, textDecoration = TextDecoration.Underline)) {
                            append("Términos y condiciones")
                        }
                        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                            append(" de uso de la plataforma.")
                        }
                    },
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón Registrar
            Button(
                onClick = alRegistrar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = formularioValido,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MoradoPrimario,
                    disabledContainerColor = MoradoPrimario.copy(alpha = 0.1f)
                )
            ) {
                Text(
                    text = "Registrar",
                    color = if (formularioValido) Color.White else MoradoPrimario.copy(alpha = 0.5f)
                )
            }
        }
    }
}
