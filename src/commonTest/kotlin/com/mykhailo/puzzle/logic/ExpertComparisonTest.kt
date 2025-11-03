package com.mykhailo.puzzle.logic

import com.mykhailo.puzzle.model.GameState
import com.mykhailo.puzzle.model.LetterStatus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class ExpertComparisonTest {
    
    @Test
    fun testAnalyzeLetters() {
        val results = ExpertComparison.analyzeLetters("hello", "hello")
        assertEquals(5, results.size)
        results.forEach { assertEquals(LetterStatus.CORRECT, it.status) }
    }
    
    @Test
    fun testAnalyzeLettersWithDifferences() {
        val results = ExpertComparison.analyzeLetters("hello", "world")
        assertEquals(5, results.size)
        
        // 'h' should be absent (not in "world")
        assertEquals(LetterStatus.ABSENT, results[0].status)
        // 'e' should be absent (not in "world")
        assertEquals(LetterStatus.ABSENT, results[1].status)
        // 'l' should be present (in "world" but wrong position)
        assertEquals(LetterStatus.PRESENT, results[2].status)
        // Second 'l' should be correct (matches position in "world")
        assertEquals(LetterStatus.CORRECT, results[3].status)
        // 'o' should be present (in "world" but wrong position)
        assertEquals(LetterStatus.PRESENT, results[4].status)
    }
    
    @Test
    fun testCalculateSimilarity() {
        assertEquals(1.0, ExpertComparison.calculateSimilarity("hello", "hello"))
        assertEquals(0.0, ExpertComparison.calculateSimilarity("", "hello"))
        assertEquals(0.0, ExpertComparison.calculateSimilarity("hello", ""))
        assertEquals(1.0, ExpertComparison.calculateSimilarity("", ""))
        
        val similarity = ExpertComparison.calculateSimilarity("hello", "hallo")
        assertTrue(similarity > 0.7) // Should be high similarity
        
        val lowSimilarity = ExpertComparison.calculateSimilarity("hello", "world")
        assertTrue(lowSimilarity < 0.5) // Should be low similarity
    }
    
    @Test
    fun testSuggestOptimalMove() {
        val state = GameState("hello", "hello")
        assertEquals(null, ExpertComparison.suggestOptimalMove(state))
        
        val state2 = GameState("hello", "hallo")
        val suggestion = ExpertComparison.suggestOptimalMove(state2)
        assertTrue(suggestion != null)
        assertTrue(suggestion!!.contains("Exchange"))
    }
    
    @Test
    fun testEvaluateEfficiency() {
        val perfectState = GameState("world", "world", 0, emptyList())
        assertEquals(1.0, ExpertComparison.evaluateEfficiency(perfectState))
        
        val incompleteState = GameState("hello", "world", 2, emptyList())
        assertEquals(0.0, ExpertComparison.evaluateEfficiency(incompleteState))
    }
    
    @Test
    fun testIsExpertSolution() {
        val expertState = GameState("world", "world", 1, listOf(
            com.mykhailo.puzzle.model.Move.exchange(0, 'w', 1)
        ))
        assertTrue(ExpertComparison.isExpertSolution(expertState))
        
        val incompleteState = GameState("hello", "world", 2, emptyList())
        assertFalse(ExpertComparison.isExpertSolution(incompleteState))
    }
    
    @Test
    fun testAnalyzeGameState() {
        val state = GameState("hello", "world", 2, listOf(
            com.mykhailo.puzzle.model.Move.exchange(0, 'w', 1),
            com.mykhailo.puzzle.model.Move.exchange(1, 'o', 1)
        ))
        
        val analysis = ExpertComparison.analyzeGameState(state)
        assertTrue(analysis.contains("Current: hello"))
        assertTrue(analysis.contains("Target:  world"))
        assertTrue(analysis.contains("Cost:    2"))
        assertTrue(analysis.contains("Moves:   2"))
        assertTrue(analysis.contains("Complete: false"))
    }
}