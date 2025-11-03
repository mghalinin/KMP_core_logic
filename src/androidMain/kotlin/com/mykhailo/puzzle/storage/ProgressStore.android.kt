package com.mykhailo.puzzle.storage

import com.mykhailo.puzzle.model.GameState
import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

actual class ProgressStore(private val context: Context) {
    
    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences("puzzle_game_progress", Context.MODE_PRIVATE)
    }
    
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }
    
    actual suspend fun saveGameState(gameId: String, state: GameState) = withContext(Dispatchers.IO) {
        val jsonString = ProgressStoreCommon.serializeGameState(state)
        prefs.edit().putString(gameId, jsonString).apply()
    }
    
    actual suspend fun loadGameState(gameId: String): GameState? = withContext(Dispatchers.IO) {
        val jsonString = prefs.getString(gameId, null) ?: return@withContext null
        return@withContext try {
            ProgressStoreCommon.deserializeGameState(jsonString)
        } catch (e: Exception) {
            null
        }
    }
    
    actual suspend fun deleteGameState(gameId: String) = withContext(Dispatchers.IO) {
        prefs.edit().remove(gameId).apply()
    }
    
    actual suspend fun getAllGameIds(): List<String> = withContext(Dispatchers.IO) {
        prefs.all.keys.toList()
    }
}