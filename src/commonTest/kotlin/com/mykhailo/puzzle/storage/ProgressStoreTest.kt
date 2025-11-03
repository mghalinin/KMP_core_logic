package com.mykhailo.puzzle.storage

import com.mykhailo.puzzle.model.GameState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class ProgressStoreTest {
    
    @Test
    fun testSerializeDeserializeGameState() {
        val originalState = GameState(
            currentWord = "hello",
            targetWord = "world",
            totalCost = 3,
            moves = listOf(
                com.mykhailo.puzzle.model.Move.delete(0, 1),
                com.mykhailo.puzzle.model.Move.move(1, 0, 1),
                com.mykhailo.puzzle.model.Move.exchange(2, 'x', 1)
            )
        )
        
        val serialized = ProgressStoreCommon.serializeGameState(originalState)
        val deserialized = ProgressStoreCommon.deserializeGameState(serialized)
        
        assertEquals(originalState, deserialized)
    }
    
    @Test
    fun testGenerateGameId() {
        val gameId = ProgressStoreCommon.generateGameId("hello", "world")
        assertTrue(gameId.startsWith("hello_to_world_"))
        assertTrue(gameId.length > "hello_to_world_".length)
    }
    
    @Test
    fun testIsValidGameStateJson() {
        val validState = GameState("hello", "world")
        val validJson = ProgressStoreCommon.serializeGameState(validState)
        assertTrue(ProgressStoreCommon.isValidGameStateJson(validJson))
        
        val invalidJson = "{invalid json"
        assertFalse(ProgressStoreCommon.isValidGameStateJson(invalidJson))
        
        val incompleteJson = "{\"currentWord\": \"hello\"}"
        assertFalse(ProgressStoreCommon.isValidGameStateJson(incompleteJson))
    }
    
    @Test
    fun testSerializationFormat() {
        val state = GameState("test", "demo", 1, listOf(
            com.mykhailo.puzzle.model.Move.delete(0, 1)
        ))
        
        val json = ProgressStoreCommon.serializeGameState(state)
        assertTrue(json.contains("\"currentWord\": \"test\""))
        assertTrue(json.contains("\"targetWord\": \"demo\""))
        assertTrue(json.contains("\"totalCost\": 1"))
        assertTrue(json.contains("\"moves\""))
    }
}