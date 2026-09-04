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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecordatorioViewModel(application: Application) : AndroidViewModel(application) {
    private val gestorPersistencia = GestorPersistencia(application)
    private val _recordatorios = MutableStateFlow<List<Recordatorio>>(emptyList())
    val recordatorios: StateFlow<List<Recordatorio>> = _recordatorios.asStateFlow()

    init {
        viewModelScope.launch {
            gestorPersistencia.recordatoriosFlow.collectLatest { listaCargada ->
                if (listaCargada.isEmpty() && _recordatorios.value.isEmpty()) {
                    // Datos iniciales si está vacío por primera vez
                    val iniciales = listOf(
                        Recordatorio(titulo = "Reunión de equipo", fecha = "8 sep 2026", prioridad = Prioridad.ALTA, descripcion = "Presentación del avance del proyecto ante todos los integrantes del Equipo 4."),
                        Recordatorio(titulo = "Entrega de reporte", fecha = "12 sep 2026", prioridad = Prioridad.MEDIA, descripcion = "Enviar el reporte mensual detallado."),
                        Recordatorio(titulo = "Revisión de diseño", fecha = "15 sep 2026", prioridad = Prioridad.BAJA, descripcion = "Revisar los nuevos prototipos de la interfaz.")
                    ).sortedBy { it.prioridad.valor }
                    _recordatorios.value = iniciales
                    gestorPersistencia.guardarRecordatorios(iniciales)
                } else {
                    _recordatorios.value = listaCargada.sortedBy { it.prioridad.valor }
                }
            }
        }
    }

    fun agregarRecordatorio(recordatorio: Recordatorio) {
        viewModelScope.launch {
            val nuevaLista = (_recordatorios.value + recordatorio).sortedBy { r -> r.prioridad.valor }
            _recordatorios.value = nuevaLista
            gestorPersistencia.guardarRecordatorios(nuevaLista)
        }
    }

    fun eliminarRecordatorio(id: String) {
        viewModelScope.launch {
            val nuevaLista = _recordatorios.value.filter { r -> r.id != id }
            _recordatorios.value = nuevaLista
            gestorPersistencia.guardarRecordatorios(nuevaLista)
        }
    }
}
