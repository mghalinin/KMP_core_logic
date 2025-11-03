package com.mykhailo.puzzle.storage

import com.mykhailo.puzzle.model.GameState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.Foundation.NSUserDefaults
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.dataUsingEncoding

actual class ProgressStore {
    
    private val userDefaults = NSUserDefaults.standardUserDefaults
    private val keyPrefix = "puzzle_game_"
    
    actual suspend fun saveGameState(gameId: String, state: GameState) = withContext(Dispatchers.Main) {
        val jsonString = ProgressStoreCommon.serializeGameState(state)
        val key = keyPrefix + gameId
        userDefaults.setObject(jsonString, key)
        userDefaults.synchronize()
    }
    
    actual suspend fun loadGameState(gameId: String): GameState? = withContext(Dispatchers.Main) {
        val key = keyPrefix + gameId
        val jsonString = userDefaults.stringForKey(key) ?: return@withContext null
        return@withContext try {
            ProgressStoreCommon.deserializeGameState(jsonString)
        } catch (e: Exception) {
            null
        }
    }
    
    actual suspend fun deleteGameState(gameId: String) = withContext(Dispatchers.Main) {
        val key = keyPrefix + gameId
        userDefaults.removeObjectForKey(key)
        userDefaults.synchronize()
    }
    
    actual suspend fun getAllGameIds(): List<String> = withContext(Dispatchers.Main) {
        val allKeys = userDefaults.dictionaryRepresentation().keys
        return@withContext allKeys
            .mapNotNull { it as? String }
            .filter { it.startsWith(keyPrefix) }
            .map { it.removePrefix(keyPrefix) }
    }
}