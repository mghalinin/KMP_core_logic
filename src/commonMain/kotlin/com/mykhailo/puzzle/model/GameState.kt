package com.mykhailo.puzzle.model

import kotlinx.serialization.Serializable

@Serializable
data class GameState(
    val currentWord: String,
    val targetWord: String,
    val totalCost: Int = 0,
    val moves: List<Move> = emptyList()
) {
    val isComplete: Boolean
        get() = currentWord == targetWord
    
    val remainingOperations: Int
        get() = if (isComplete) 0 else calculateMinimumOperations()
    
    private fun calculateMinimumOperations(): Int {
        // Simple estimation - can be improved with more sophisticated algorithms
        val current = currentWord.toCharArray()
        val target = targetWord.toCharArray()
        
        if (current.size == target.size) {
            return current.zip(target).count { (c, t) -> c != t }
        }
        
        return minOf(current.size, target.size) + kotlin.math.abs(current.size - target.size)
    }
    
    fun withMove(move: Move, newWord: String): GameState {
        return copy(
            currentWord = newWord,
            totalCost = totalCost + move.cost,
            moves = moves + move
        )
    }
}