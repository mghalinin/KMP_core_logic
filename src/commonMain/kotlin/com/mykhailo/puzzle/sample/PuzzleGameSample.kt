package com.mykhailo.puzzle.sample

import com.mykhailo.puzzle.logic.GameEngine
import com.mykhailo.puzzle.logic.ExpertComparison

/**
 * Sample console application demonstrating the puzzle game functionality.
 * This validates that the shared core works identically across platforms.
 */
object PuzzleGameSample {
    
    fun runSample(): String {
        val output = StringBuilder()
        
        output.appendLine("=== Puzzle Game Sample - Cross Platform Validation ===")
        output.appendLine()
        
        // Sample 1: Simple word transformation
        output.appendLine("--- Sample 1: 'hello' → 'world' ---")
        var state = GameEngine.createGame("hello", "world")
        output.appendLine("Initial: ${state.currentWord} (Cost: ${state.totalCost})")
        
        // Transform step by step
        state = GameEngine.exchangeLetter(state, 0, 'w')  // hello → wello
        output.appendLine("Step 1:  ${state.currentWord} (Cost: ${state.totalCost})")
        
        state = GameEngine.exchangeLetter(state, 1, 'o')  // wello → wollo  
        output.appendLine("Step 2:  ${state.currentWord} (Cost: ${state.totalCost})")
        
        state = GameEngine.exchangeLetter(state, 2, 'r')  // wollo → worlo
        output.appendLine("Step 3:  ${state.currentWord} (Cost: ${state.totalCost})")
        
        state = GameEngine.exchangeLetter(state, 4, 'd')  // worlo → world
        output.appendLine("Step 4:  ${state.currentWord} (Cost: ${state.totalCost})")
        
        output.appendLine("Complete: ${GameEngine.checkWin(state)}")
        output.appendLine()
        
        // Sample 2: Letter deletion
        output.appendLine("--- Sample 2: 'hello' → 'hell' ---")
        var state2 = GameEngine.createGame("hello", "hell")
        output.appendLine("Initial: ${state2.currentWord} (Cost: ${state2.totalCost})")
        
        state2 = GameEngine.deleteLetter(state2, 4)  // hello → hell
        output.appendLine("Delete:  ${state2.currentWord} (Cost: ${state2.totalCost})")
        output.appendLine("Complete: ${GameEngine.checkWin(state2)}")
        output.appendLine()
        
        // Sample 3: Letter movement
        output.appendLine("--- Sample 3: 'hello' → 'ehllo' ---")
        var state3 = GameEngine.createGame("hello", "ehllo")
        output.appendLine("Initial: ${state3.currentWord} (Cost: ${state3.totalCost})")
        
        state3 = GameEngine.moveLetter(state3, 1, 0)  // hello → ehllo
        output.appendLine("Move:    ${state3.currentWord} (Cost: ${state3.totalCost})")
        output.appendLine("Complete: ${GameEngine.checkWin(state3)}")
        output.appendLine()
        
        // Expert Analysis
        output.appendLine("--- Expert Analysis ---")
        output.appendLine(ExpertComparison.analyzeGameState(state))
        
        // Cost validation
        output.appendLine("--- Cost Validation ---")
        output.appendLine("Sample 1 total cost: ${GameEngine.calculateCost(state)}")
        output.appendLine("Sample 2 total cost: ${GameEngine.calculateCost(state2)}")
        output.appendLine("Sample 3 total cost: ${GameEngine.calculateCost(state3)}")
        
        return output.toString()
    }
    
    fun validateConsistency(): Boolean {
        return try {
            // Test all major operations
            val testResults = mutableListOf<Boolean>()
            
            // Test 1: Basic transformation
            var state = GameEngine.createGame("abc", "def")
            state = GameEngine.exchangeLetter(state, 0, 'd')
            state = GameEngine.exchangeLetter(state, 1, 'e')
            state = GameEngine.exchangeLetter(state, 2, 'f')
            testResults.add(GameEngine.checkWin(state))
            testResults.add(state.totalCost == 3)
            
            // Test 2: Deletion
            state = GameEngine.createGame("abcd", "abc")
            state = GameEngine.deleteLetter(state, 3)
            testResults.add(GameEngine.checkWin(state))
            testResults.add(state.totalCost == 1)
            
            // Test 3: Movement
            state = GameEngine.createGame("abc", "bac")
            state = GameEngine.moveLetter(state, 1, 0)
            testResults.add(GameEngine.checkWin(state))
            testResults.add(state.totalCost == 1)
            
            // All tests must pass
            testResults.all { it }
        } catch (e: Exception) {
            false
        }
    }
}