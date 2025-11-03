package com.mykhailo.puzzle.logic

import com.mykhailo.puzzle.model.GameState
import com.mykhailo.puzzle.model.Move

/**
 * Core game engine that handles game state transitions and operations.
 */
object GameEngine {
    
    /**
     * Deletes a letter at the specified index and returns the new game state.
     * 
     * @param state Current game state
     * @param index Index of the letter to delete
     * @return New game state after the delete operation
     */
    fun deleteLetter(state: GameState, index: Int): GameState {
        val cost = CostCalculator.calculateDeleteCost(state.currentWord, index)
        val move = Move.delete(index, cost)
        val newWord = WordTransformer.deleteLetter(state.currentWord, index)
        
        return state.withMove(move, newWord)
    }
    
    /**
     * Moves a letter from one position to another and returns the new game state.
     * 
     * @param state Current game state
     * @param from Source index
     * @param to Destination index
     * @return New game state after the move operation
     */
    fun moveLetter(state: GameState, from: Int, to: Int): GameState {
        val cost = CostCalculator.calculateMoveCost(state.currentWord, from, to)
        val move = Move.move(from, to, cost)
        val newWord = WordTransformer.moveLetter(state.currentWord, from, to)
        
        return state.withMove(move, newWord)
    }
    
    /**
     * Exchanges a letter at the specified index with a new character and returns the new game state.
     * 
     * @param state Current game state
     * @param index Index of the letter to exchange
     * @param newChar New character to replace with
     * @return New game state after the exchange operation
     */
    fun exchangeLetter(state: GameState, index: Int, newChar: Char): GameState {
        val cost = CostCalculator.calculateExchangeCost(state.currentWord, index, newChar)
        val move = Move.exchange(index, newChar, cost)
        val newWord = WordTransformer.exchangeLetter(state.currentWord, index, newChar)
        
        return state.withMove(move, newWord)
    }
    
    /**
     * Calculates the current total cost of all moves made so far.
     * 
     * @param state Current game state
     * @return Total cost of all moves
     */
    fun calculateCost(state: GameState): Int {
        return state.totalCost
    }
    
    /**
     * Checks if the current word matches the target word (win condition).
     * 
     * @param state Current game state
     * @return True if the puzzle is solved, false otherwise
     */
    fun checkWin(state: GameState): Boolean {
        return state.currentWord == state.targetWord
    }
    
    /**
     * Creates a new game with the specified starting and target words.
     * 
     * @param startWord The initial word
     * @param targetWord The target word to achieve
     * @return Initial game state
     */
    fun createGame(startWord: String, targetWord: String): GameState {
        return GameState(
            currentWord = startWord,
            targetWord = targetWord,
            totalCost = 0,
            moves = emptyList()
        )
    }
    
    /**
     * Validates if a move is legal given the current game state.
     * 
     * @param state Current game state
     * @param move Move to validate
     * @return True if the move is valid, false otherwise
     */
    fun isValidMove(state: GameState, move: Move): Boolean {
        return try {
            when (move.type) {
                com.mykhailo.puzzle.model.MoveType.DELETE -> {
                    move.fromIndex != null && 
                    move.fromIndex >= 0 && 
                    move.fromIndex < state.currentWord.length
                }
                com.mykhailo.puzzle.model.MoveType.MOVE -> {
                    move.fromIndex != null && 
                    move.toIndex != null &&
                    move.fromIndex >= 0 && 
                    move.fromIndex < state.currentWord.length &&
                    move.toIndex >= 0 && 
                    move.toIndex < state.currentWord.length
                }
                com.mykhailo.puzzle.model.MoveType.EXCHANGE -> {
                    move.fromIndex != null && 
                    move.newChar != null &&
                    move.fromIndex >= 0 && 
                    move.fromIndex < state.currentWord.length
                }
            }
        } catch (e: Exception) {
            false
        }
    }
}