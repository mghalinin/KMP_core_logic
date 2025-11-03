package com.mykhailo.puzzle.storage

import com.mykhailo.puzzle.model.GameState
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString

/**
 * Handles persistence of game progress and state.
 * Platform-specific implementations will provide actual storage mechanisms.
 */
expect class ProgressStore() {
    suspend fun saveGameState(gameId: String, state: GameState)
    suspend fun loadGameState(gameId: String): GameState?
    suspend fun deleteGameState(gameId: String)
    suspend fun getAllGameIds(): List<String>
}

/**
 * Common functionality for progress storage that can be shared across platforms.
 */
object ProgressStoreCommon {
    
    private val json = Json { 
        prettyPrint = true
        ignoreUnknownKeys = true 
    }
    
    /**
     * Serializes a GameState to JSON string.
     */
    fun serializeGameState(state: GameState): String {
        return json.encodeToString(state)
    }
    
    /**
     * Deserializes a GameState from JSON string.
     */
    fun deserializeGameState(jsonString: String): GameState {
        return json.decodeFromString(jsonString)
    }
    
    /**
     * Generates a unique game ID based on start and target words.
     */
    fun generateGameId(startWord: String, targetWord: String): String {
        return "${startWord}_to_${targetWord}_${System.currentTimeMillis()}"
    }
    
    /**
     * Validates if a serialized game state is valid.
     */
    fun isValidGameStateJson(jsonString: String): Boolean {
        return try {
            deserializeGameState(jsonString)
            true
        } catch (e: Exception) {
            false
        }
    }
}