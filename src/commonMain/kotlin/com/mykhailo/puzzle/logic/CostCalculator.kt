package com.mykhailo.puzzle.logic

import com.mykhailo.puzzle.model.Move
import com.mykhailo.puzzle.model.MoveType

/**
 * Calculates costs for different types of moves in the puzzle game.
 */
object CostCalculator {
    
    /**
     * Base cost for delete operations
     */
    const val DELETE_COST = 1
    
    /**
     * Base cost for move operations
     */
    const val MOVE_COST = 1
    
    /**
     * Base cost for exchange operations
     */
    const val EXCHANGE_COST = 1
    
    /**
     * Calculates the cost of a delete operation at the specified index.
     */
    fun calculateDeleteCost(word: String, index: Int): Int {
        if (index < 0 || index >= word.length) {
            throw IndexOutOfBoundsException("Index $index is out of bounds for word of length ${word.length}")
        }
        return DELETE_COST
    }
    
    /**
     * Calculates the cost of moving a letter from one position to another.
     */
    fun calculateMoveCost(word: String, from: Int, to: Int): Int {
        if (from < 0 || from >= word.length) {
            throw IndexOutOfBoundsException("From index $from is out of bounds for word of length ${word.length}")
        }
        if (to < 0 || to >= word.length) {
            throw IndexOutOfBoundsException("To index $to is out of bounds for word of length ${word.length}")
        }
        return MOVE_COST
    }
    
    /**
     * Calculates the cost of exchanging a letter at the specified position.
     */
    fun calculateExchangeCost(word: String, index: Int, newChar: Char): Int {
        if (index < 0 || index >= word.length) {
            throw IndexOutOfBoundsException("Index $index is out of bounds for word of length ${word.length}")
        }
        return EXCHANGE_COST
    }
    
    /**
     * Calculates the total cost of a series of moves.
     */
    fun calculateTotalCost(moves: List<Move>): Int {
        return moves.sumOf { it.cost }
    }
    
    /**
     * Estimates the minimum cost to transform one word into another using optimal moves.
     * This is a heuristic and may not always give the absolute minimum.
     */
    fun estimateMinimumCost(current: String, target: String): Int {
        if (current == target) return 0
        
        val currentChars = current.toMutableList()
        val targetChars = target.toList()
        var cost = 0
        
        // Handle length differences first
        while (currentChars.size > targetChars.size) {
            currentChars.removeLastOrNull()
            cost += DELETE_COST
        }
        
        // Count character differences for exchanges
        for (i in currentChars.indices) {
            if (i < targetChars.size && currentChars[i] != targetChars[i]) {
                cost += EXCHANGE_COST
            }
        }
        
        return cost
    }
}