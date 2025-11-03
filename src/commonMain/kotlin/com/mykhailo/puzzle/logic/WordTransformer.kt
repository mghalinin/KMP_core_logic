package com.mykhailo.puzzle.logic

import com.mykhailo.puzzle.model.GameState
import com.mykhailo.puzzle.model.Move

/**
 * Handles word transformations through various move operations.
 */
object WordTransformer {
    
    /**
     * Deletes a letter at the specified index from a word.
     * 
     * @param word The source word
     * @param index The index of the character to delete
     * @return The word with the character removed
     * @throws IndexOutOfBoundsException if index is invalid
     */
    fun deleteLetter(word: String, index: Int): String {
        if (index < 0 || index >= word.length) {
            throw IndexOutOfBoundsException("Index $index is out of bounds for word of length ${word.length}")
        }
        return word.removeRange(index, index + 1)
    }
    
    /**
     * Moves a letter from one position to another within a word.
     * 
     * @param word The source word
     * @param from The source index
     * @param to The destination index
     * @return The word with the character moved
     * @throws IndexOutOfBoundsException if either index is invalid
     */
    fun moveLetter(word: String, from: Int, to: Int): String {
        if (from < 0 || from >= word.length) {
            throw IndexOutOfBoundsException("From index $from is out of bounds for word of length ${word.length}")
        }
        if (to < 0 || to >= word.length) {
            throw IndexOutOfBoundsException("To index $to is out of bounds for word of length ${word.length}")
        }
        
        if (from == to) return word
        
        val chars = word.toMutableList()
        val charToMove = chars.removeAt(from)
        
        // Adjust insertion index if we removed from before the target
        val insertIndex = if (from < to) to - 1 else to
        chars.add(insertIndex, charToMove)
        
        return chars.joinToString("")
    }
    
    /**
     * Exchanges (replaces) a letter at the specified index with a new character.
     * 
     * @param word The source word
     * @param index The index of the character to replace
     * @param newChar The new character
     * @return The word with the character replaced
     * @throws IndexOutOfBoundsException if index is invalid
     */
    fun exchangeLetter(word: String, index: Int, newChar: Char): String {
        if (index < 0 || index >= word.length) {
            throw IndexOutOfBoundsException("Index $index is out of bounds for word of length ${word.length}")
        }
        
        return word.substring(0, index) + newChar + word.substring(index + 1)
    }
    
    /**
     * Applies a move to a word and returns the result.
     * 
     * @param word The source word
     * @param move The move to apply
     * @return The transformed word
     */
    fun applyMove(word: String, move: Move): String {
        return when (move.type) {
            com.mykhailo.puzzle.model.MoveType.DELETE -> {
                requireNotNull(move.fromIndex) { "Delete move requires fromIndex" }
                deleteLetter(word, move.fromIndex)
            }
            com.mykhailo.puzzle.model.MoveType.MOVE -> {
                requireNotNull(move.fromIndex) { "Move operation requires fromIndex" }
                requireNotNull(move.toIndex) { "Move operation requires toIndex" }
                moveLetter(word, move.fromIndex, move.toIndex)
            }
            com.mykhailo.puzzle.model.MoveType.EXCHANGE -> {
                requireNotNull(move.fromIndex) { "Exchange move requires fromIndex" }
                requireNotNull(move.newChar) { "Exchange move requires newChar" }
                exchangeLetter(word, move.fromIndex, move.newChar)
            }
        }
    }
}