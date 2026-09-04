package com.example.appderegistrodeeventosrecordatorio.ui.vista_modelo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appderegistrodeeventosrecordatorio.datos.GestorPersistencia
import com.example.appderegistrodeeventosrecordatorio.datos.modelo.Prioridad
import com.example.appderegistrodeeventosrecordatorio.datos.modelo.Recordatorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class RecordatorioViewModel(application: Application) : AndroidViewModel(application) {
    private val gestorPersistencia = GestorPersistencia(application)
    private val _recordatorios = MutableStateFlow<List<Recordatorio>>(emptyList())
    val recordatorios: StateFlow<List<Recordatorio>> = _recordatorios.asStateFlow()

    init {
        viewModelScope.launch {
            // Combinamos ambos flujos para reaccionar a cualquier cambio
            combine(
                gestorPersistencia.esPrimeraVezFlow,
                gestorPersistencia.recordatoriosFlow
            ) { esPrimeraVez, listaCargada ->
                if (esPrimeraVez) {
                    val iniciales = listOf(
                        Recordatorio(titulo = "Reunión de equipo", fecha = "8 sep 2026", prioridad = Prioridad.ALTA, descripcion = "Presentación del avance del proyecto ante todos los integrantes del Equipo 4."),
                        Recordatorio(titulo = "Entrega de reporte", fecha = "12 sep 2026", prioridad = Prioridad.MEDIA, descripcion = "Enviar el reporte mensual detallado."),
                        Recordatorio(titulo = "Revisión de diseño", fecha = "15 sep 2026", prioridad = Prioridad.BAJA, descripcion = "Revisar los nuevos prototipos de la interfaz.")
                    ).sortedBy { it.prioridad.valor }
                    _recordatorios.value = iniciales
                    gestorPersistencia.guardarRecordatorios(iniciales)
                    gestorPersistencia.marcarComoIniciado()
                } else {
                    _recordatorios.value = listaCargada.sortedBy { it.prioridad.valor }
                }
            }.collect {}
        }
    }

    fun agregarRecordatorio(recordatorio: Recordatorio) {
        viewModelScope.launch {
            val nuevaLista = (_recordatorios.value + recordatorio).sortedBy { r -> r.prioridad.valor }
            // No actualizamos _recordatorios.value aquí porque el flujo collect en init lo hará
            gestorPersistencia.guardarRecordatorios(nuevaLista)
        }
    }

    fun eliminarRecordatorio(id: String) {
        viewModelScope.launch {
            val nuevaLista = _recordatorios.value.filter { r -> r.id != id }
            // No actualizamos _recordatorios.value aquí porque el flujo collect en init lo hará
            gestorPersistencia.guardarRecordatorios(nuevaLista)
        }
    }
}
