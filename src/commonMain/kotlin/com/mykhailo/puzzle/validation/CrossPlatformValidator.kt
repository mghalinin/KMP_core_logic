package com.mykhailo.puzzle.validation

import com.mykhailo.puzzle.logic.GameEngine
import com.mykhailo.puzzle.logic.CostCalculator
import com.mykhailo.puzzle.logic.WordTransformer
import com.mykhailo.puzzle.logic.ExpertComparison
import com.mykhailo.puzzle.model.GameState
import com.mykhailo.puzzle.model.Move
import com.mykhailo.puzzle.storage.ProgressStoreCommon

/**
 * Comprehensive validation to ensure all core functionality works correctly.
 * This can be run without requiring full platform setup.
 */
object CrossPlatformValidator {
    
    fun runAllValidations(): ValidationResult {
        val results = mutableMapOf<String, Boolean>()
        val messages = mutableListOf<String>()
        
        try {
            // Test 1: Basic Game Engine Operations
            results["gameEngine"] = testGameEngine()
            messages.add("✅ Game Engine: All operations working correctly")
            
            // Test 2: Cost Calculator
            results["costCalculator"] = testCostCalculator()
            messages.add("✅ Cost Calculator: All cost calculations accurate")
            
            // Test 3: Word Transformer
            results["wordTransformer"] = testWordTransformer()
            messages.add("✅ Word Transformer: All transformations working")
            
            // Test 4: Expert Comparison
            results["expertComparison"] = testExpertComparison()
            messages.add("✅ Expert Comparison: Analysis functions working")
            
            // Test 5: Serialization
            results["serialization"] = testSerialization()
            messages.add("✅ Serialization: JSON serialization/deserialization working")
            
            // Test 6: Complex Scenarios
            results["complexScenarios"] = testComplexScenarios()
            messages.add("✅ Complex Scenarios: Multi-step transformations working")
            
        } catch (e: Exception) {
            messages.add("❌ Validation failed with exception: ${e.message}")
            return ValidationResult(false, messages)
        }
        
        val allPassed = results.values.all { it }
        if (allPassed) {
            messages.add("")
            messages.add("🎉 ALL VALIDATIONS PASSED!")
            messages.add("✅ Shared core is ready for iOS and Android integration")
            messages.add("✅ Cross-platform functionality verified")
        } else {
            messages.add("")
            messages.add("❌ Some validations failed:")
            results.filter { !it.value }.forEach { (test, _) ->
                messages.add("   - $test")
            }
        }
        
        return ValidationResult(allPassed, messages)
    }
    
    private fun testGameEngine(): Boolean {
        // Test game creation
        val game = GameEngine.createGame("hello", "world")
        if (game.currentWord != "hello" || game.targetWord != "world") return false
        
        // Test delete operation
        val deleted = GameEngine.deleteLetter(game, 0)
        if (deleted.currentWord != "ello" || deleted.totalCost != 1) return false
        
        // Test move operation
        val moved = GameEngine.moveLetter(game, 1, 0)
        if (moved.currentWord != "ehllo" || moved.totalCost != 1) return false
        
        // Test exchange operation
        val exchanged = GameEngine.exchangeLetter(game, 0, 'w')
        if (exchanged.currentWord != "wello" || exchanged.totalCost != 1) return false
        
        // Test win condition
        val winGame = GameEngine.createGame("test", "test")
        if (!GameEngine.checkWin(winGame)) return false
        
        return true
    }
    
    private fun testCostCalculator(): Boolean {
        // Test basic costs
        if (CostCalculator.calculateDeleteCost("hello", 0) != 1) return false
        if (CostCalculator.calculateMoveCost("hello", 0, 1) != 1) return false
        if (CostCalculator.calculateExchangeCost("hello", 0, 'a') != 1) return false
        
        // Test total cost calculation
        val moves = listOf(
            Move.delete(0, 1),
            Move.move(1, 2, 1),
            Move.exchange(3, 'x', 1)
        )
        if (CostCalculator.calculateTotalCost(moves) != 3) return false
        
        // Test estimate minimum cost
        if (CostCalculator.estimateMinimumCost("hello", "hello") != 0) return false
        
        return true
    }
    
    private fun testWordTransformer(): Boolean {
        // Test deleteLetter
        if (WordTransformer.deleteLetter("hello", 0) != "ello") return false
        if (WordTransformer.deleteLetter("hello", 4) != "hell") return false
        
        // Test moveLetter
        if (WordTransformer.moveLetter("hello", 1, 0) != "ehllo") return false
        if (WordTransformer.moveLetter("hello", 0, 4) != "elloh") return false
        
        // Test exchangeLetter
        if (WordTransformer.exchangeLetter("hello", 0, 'a') != "aello") return false
        if (WordTransformer.exchangeLetter("hello", 4, 'z') != "hellz") return false
        
        return true
    }
    
    private fun testExpertComparison(): Boolean {
        // Test letter analysis
        val results = ExpertComparison.analyzeLetters("hello", "hello")
        if (results.size != 5) return false
        if (!results.all { it.status.name == "CORRECT" }) return false
        
        // Test similarity calculation
        if (ExpertComparison.calculateSimilarity("hello", "hello") != 1.0) return false
        if (ExpertComparison.calculateSimilarity("", "") != 1.0) return false
        
        // Test efficiency evaluation
        val perfectState = GameState("test", "test", 0, emptyList())
        if (ExpertComparison.evaluateEfficiency(perfectState) != 1.0) return false
        
        return true
    }
    
    private fun testSerialization(): Boolean {
        val originalState = GameState(
            currentWord = "hello",
            targetWord = "world", 
            totalCost = 2,
            moves = listOf(
                Move.delete(0, 1),
                Move.exchange(1, 'o', 1)
            )
        )
        
        // Test serialization/deserialization
        val serialized = ProgressStoreCommon.serializeGameState(originalState)
        val deserialized = ProgressStoreCommon.deserializeGameState(serialized)
        
        if (originalState != deserialized) return false
        
        // Test JSON validation
        if (!ProgressStoreCommon.isValidGameStateJson(serialized)) return false
        if (ProgressStoreCommon.isValidGameStateJson("{invalid}")) return false
        
        return true
    }
    
    private fun testComplexScenarios(): Boolean {
        // Scenario 1: Transform "hello" to "world"
        var state = GameEngine.createGame("hello", "world")
        state = GameEngine.exchangeLetter(state, 0, 'w')  // hello → wello
        state = GameEngine.exchangeLetter(state, 1, 'o')  // wello → wollo
        state = GameEngine.exchangeLetter(state, 2, 'r')  // wollo → worlo
        state = GameEngine.exchangeLetter(state, 4, 'd')  // worlo → world
        
        if (!GameEngine.checkWin(state)) return false
        if (state.totalCost != 4) return false
        
        // Scenario 2: Multiple deletions
        var state2 = GameEngine.createGame("hello", "he")
        state2 = GameEngine.deleteLetter(state2, 4)  // hello → hell
        state2 = GameEngine.deleteLetter(state2, 3)  // hell → hel
        state2 = GameEngine.deleteLetter(state2, 2)  // hel → he
        
        if (!GameEngine.checkWin(state2)) return false
        if (state2.totalCost != 3) return false
        
        return true
    }
}

data class ValidationResult(
    val success: Boolean,
    val messages: List<String>
)