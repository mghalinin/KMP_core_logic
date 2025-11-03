package com.mykhailo.puzzle.logic

import com.mykhailo.puzzle.model.GameState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class GameEngineTest {
    
    @Test
    fun testCreateGame() {
        val state = GameEngine.createGame("hello", "world")
        assertEquals("hello", state.currentWord)
        assertEquals("world", state.targetWord)
        assertEquals(0, state.totalCost)
        assertTrue(state.moves.isEmpty())
    }
    
    @Test
    fun testDeleteLetter() {
        val initialState = GameEngine.createGame("hello", "hell")
        val newState = GameEngine.deleteLetter(initialState, 4)
        
        assertEquals("hell", newState.currentWord)
        assertEquals("hell", newState.targetWord)
        assertEquals(1, newState.totalCost)
        assertEquals(1, newState.moves.size)
        assertEquals(com.mykhailo.puzzle.model.MoveType.DELETE, newState.moves[0].type)
    }
    
    @Test
    fun testMoveLetter() {
        val initialState = GameEngine.createGame("hello", "ehllo")
        val newState = GameEngine.moveLetter(initialState, 1, 0)
        
        assertEquals("ehllo", newState.currentWord)
        assertEquals("ehllo", newState.targetWord)
        assertEquals(1, newState.totalCost)
        assertEquals(1, newState.moves.size)
        assertEquals(com.mykhailo.puzzle.model.MoveType.MOVE, newState.moves[0].type)
    }
    
    @Test
    fun testExchangeLetter() {
        val initialState = GameEngine.createGame("hello", "hallo")
        val newState = GameEngine.exchangeLetter(initialState, 1, 'a')
        
        assertEquals("hallo", newState.currentWord)
        assertEquals("hallo", newState.targetWord)
        assertEquals(1, newState.totalCost)
        assertEquals(1, newState.moves.size)
        assertEquals(com.mykhailo.puzzle.model.MoveType.EXCHANGE, newState.moves[0].type)
    }
    
    @Test
    fun testCalculateCost() {
        val initialState = GameEngine.createGame("hello", "world")
        val state1 = GameEngine.deleteLetter(initialState, 0)
        val state2 = GameEngine.exchangeLetter(state1, 0, 'w')
        
        assertEquals(2, GameEngine.calculateCost(state2))
    }
    
    @Test
    fun testCheckWin() {
        val winState = GameEngine.createGame("hello", "hello")
        assertTrue(GameEngine.checkWin(winState))
        
        val notWinState = GameEngine.createGame("hello", "world")
        assertFalse(GameEngine.checkWin(notWinState))
    }
    
    @Test
    fun testIsValidMove() {
        val state = GameEngine.createGame("hello", "world")
        
        val validDelete = com.mykhailo.puzzle.model.Move.delete(0, 1)
        assertTrue(GameEngine.isValidMove(state, validDelete))
        
        val invalidDelete = com.mykhailo.puzzle.model.Move.delete(10, 1)
        assertFalse(GameEngine.isValidMove(state, invalidDelete))
        
        val validMove = com.mykhailo.puzzle.model.Move.move(0, 1, 1)
        assertTrue(GameEngine.isValidMove(state, validMove))
        
        val validExchange = com.mykhailo.puzzle.model.Move.exchange(0, 'w', 1)
        assertTrue(GameEngine.isValidMove(state, validExchange))
    }
    
    @Test
    fun testComplexTransformation() {
        // Test transforming "hello" to "world"
        var state = GameEngine.createGame("hello", "world")
        
        // Replace 'h' with 'w'
        state = GameEngine.exchangeLetter(state, 0, 'w')
        assertEquals("wello", state.currentWord)
        
        // Replace 'e' with 'o' 
        state = GameEngine.exchangeLetter(state, 1, 'o')
        assertEquals("wollo", state.currentWord)
        
        // Replace first 'l' with 'r'
        state = GameEngine.exchangeLetter(state, 2, 'r')
        assertEquals("worlo", state.currentWord)
        
        // Replace second 'l' with 'l' (no change needed)
        // Replace 'o' with 'd'
        state = GameEngine.exchangeLetter(state, 4, 'd')
        assertEquals("world", state.currentWord)
        
        assertTrue(GameEngine.checkWin(state))
        assertEquals(4, GameEngine.calculateCost(state))
    }
}