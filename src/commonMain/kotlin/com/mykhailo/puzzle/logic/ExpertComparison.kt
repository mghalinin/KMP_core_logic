package com.mykhailo.puzzle.logic

import com.mykhailo.puzzle.model.GameState
import com.mykhailo.puzzle.model.LetterResult
import com.mykhailo.puzzle.model.LetterStatus

/**
 * Provides expert-level analysis and comparison functionality for puzzle solutions.
 */
object ExpertComparison {
    
    /**
     * Analyzes the current word against the target and returns detailed letter feedback.
     * Similar to Wordle's feedback system.
     * 
     * @param current The current word state
     * @param target The target word
     * @return List of LetterResult showing status of each letter
     */
    fun analyzeLetters(current: String, target: String): List<LetterResult> {
        val results = mutableListOf<LetterResult>()
        val targetChars = target.toCharArray()
        val currentChars = current.toCharArray()
        val targetCharCounts = target.groupingBy { it }.eachCount().toMutableMap()
        
        // First pass: Mark exact matches
        for (i in currentChars.indices) {
            if (i < targetChars.size && currentChars[i] == targetChars[i]) {
                results.add(LetterResult(i, currentChars[i], LetterStatus.CORRECT))
                targetCharCounts[currentChars[i]] = targetCharCounts[currentChars[i]]!! - 1
            } else {
                results.add(LetterResult(i, currentChars[i], LetterStatus.ABSENT)) // Placeholder
            }
        }
        
        // Second pass: Mark present letters (wrong position)
        for (i in results.indices) {
            if (results[i].status == LetterStatus.ABSENT) {
                val char = currentChars[i]
                if (targetCharCounts.getOrDefault(char, 0) > 0) {
                    results[i] = results[i].copy(status = LetterStatus.PRESENT)
                    targetCharCounts[char] = targetCharCounts[char]!! - 1
                }
            }
        }
        
        return results
    }
    
    /**
     * Calculates a similarity score between current and target words (0.0 to 1.0).
     * 
     * @param current The current word
     * @param target The target word
     * @return Similarity score where 1.0 means identical
     */
    fun calculateSimilarity(current: String, target: String): Double {
        if (current == target) return 1.0
        if (current.isEmpty() && target.isEmpty()) return 1.0
        if (current.isEmpty() || target.isEmpty()) return 0.0
        
        val maxLength = maxOf(current.length, target.length)
        val minLength = minOf(current.length, target.length)
        
        var matches = 0
        for (i in 0 until minLength) {
            if (current[i] == target[i]) {
                matches++
            }
        }
        
        return matches.toDouble() / maxLength
    }
    
    /**
     * Suggests the optimal next move based on current game state.
     * This is a heuristic and may not always provide the absolute best move.
     * 
     * @param state Current game state
     * @return Suggested move description or null if already complete
     */
    fun suggestOptimalMove(state: GameState): String? {
        if (state.isComplete) return null
        
        val current = state.currentWord
        val target = state.targetWord
        
        // Find first difference
        for (i in 0 until minOf(current.length, target.length)) {
            if (current[i] != target[i]) {
                // Check if target character exists later in current word
                val targetChar = target[i]
                val laterIndex = current.indexOf(targetChar, i + 1)
                
                return if (laterIndex != -1) {
                    "Move '${targetChar}' from position $laterIndex to position $i"
                } else {
                    "Exchange '${current[i]}' at position $i with '${targetChar}'"
                }
            }
        }
        
        // Handle length differences
        return when {
            current.length > target.length -> "Delete character at position ${target.length}"
            current.length < target.length -> "Need to insert '${target[current.length]}' (not supported in current operations)"
            else -> null
        }
    }
    
    /**
     * Evaluates the efficiency of a solution path.
     * 
     * @param state Final game state
     * @return Efficiency score from 0.0 (poor) to 1.0 (optimal)
     */
    fun evaluateEfficiency(state: GameState): Double {
        if (!state.isComplete) return 0.0
        
        val actualCost = state.totalCost
        val estimatedOptimalCost = CostCalculator.estimateMinimumCost(
            state.moves.firstOrNull()?.let { 
                // Reconstruct initial word by reversing moves (simplified)
                state.currentWord // This is simplified - in reality we'd need to track initial state
            } ?: state.currentWord,
            state.targetWord
        )
        
        return if (actualCost == 0) 1.0 else minOf(1.0, estimatedOptimalCost.toDouble() / actualCost)
    }
    
    /**
     * Checks if the current solution is considered "expert level" (highly efficient).
     * 
     * @param state Current game state
     * @return True if solution demonstrates expert-level efficiency
     */
    fun isExpertSolution(state: GameState): Boolean {
        return state.isComplete && evaluateEfficiency(state) >= 0.9
    }
    
    /**
     * Provides detailed analysis of the current game state.
     * 
     * @param state Current game state
     * @return Human-readable analysis report
     */
    fun analyzeGameState(state: GameState): String {
        val analysis = StringBuilder()
        
        analysis.appendLine("=== Game State Analysis ===")
        analysis.appendLine("Current: ${state.currentWord}")
        analysis.appendLine("Target:  ${state.targetWord}")
        analysis.appendLine("Cost:    ${state.totalCost}")
        analysis.appendLine("Moves:   ${state.moves.size}")
        analysis.appendLine("Complete: ${state.isComplete}")
        
        if (state.isComplete) {
            val efficiency = evaluateEfficiency(state)
            analysis.appendLine("Efficiency: ${String.format("%.1f%%", efficiency * 100)}")
            analysis.appendLine("Expert Level: ${isExpertSolution(state)}")
        } else {
            val similarity = calculateSimilarity(state.currentWord, state.targetWord)
            analysis.appendLine("Similarity: ${String.format("%.1f%%", similarity * 100)}")
            
            val suggestion = suggestOptimalMove(state)
            if (suggestion != null) {
                analysis.appendLine("Suggestion: $suggestion")
            }
        }
        
        return analysis.toString()
    }
}