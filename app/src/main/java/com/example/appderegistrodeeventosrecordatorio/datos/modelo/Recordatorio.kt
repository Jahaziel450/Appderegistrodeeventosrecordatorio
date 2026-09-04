package com.example.appderegistrodeeventosrecordatorio.datos.modelo

import java.util.UUID

enum class Prioridad(val nombre: String, val valor: Int) {
    ALTA("Alta", 1),
    MEDIA("Media", 2),
    BAJA("Baja", 3)
}

data class Recordatorio(
    val id: String = UUID.randomUUID().toString(),
    val titulo: String,
    val fecha: String,
    val prioridad: Prioridad,
    val descripcion: String,
    val completado: Boolean = false
)
