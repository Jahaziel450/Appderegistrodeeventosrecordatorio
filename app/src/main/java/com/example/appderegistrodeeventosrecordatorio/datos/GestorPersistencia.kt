package com.example.appderegistrodeeventosrecordatorio.datos

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.appderegistrodeeventosrecordatorio.datos.modelo.Recordatorio
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "recordatorios_prefs")

class GestorPersistencia(private val context: Context) {
    private val GSON = Gson()
    private val CLAVE_RECORDATORIOS = stringPreferencesKey("lista_recordatorios")
    private val CLAVE_PRIMERA_VEZ = androidx.datastore.preferences.core.booleanPreferencesKey("primera_vez")

    val recordatoriosFlow: Flow<List<Recordatorio>> = context.dataStore.data.map { preferencias ->
        val json = preferencias[CLAVE_RECORDATORIOS] ?: ""
        if (json.isEmpty()) {
            emptyList()
        } else {
            val tipo = object : TypeToken<List<Recordatorio>>() {}.type
            GSON.fromJson(json, tipo)
        }
    }

    val esPrimeraVezFlow: Flow<Boolean> = context.dataStore.data.map { preferencias ->
        preferencias[CLAVE_PRIMERA_VEZ] ?: true
    }

    suspend fun marcarComoIniciado() {
        context.dataStore.edit { preferencias ->
            preferencias[CLAVE_PRIMERA_VEZ] = false
        }
    }

    suspend fun guardarRecordatorios(lista: List<Recordatorio>) {
        val json = GSON.toJson(lista)
        context.dataStore.edit { preferencias ->
            preferencias[CLAVE_RECORDATORIOS] = json
        }
    }
}
