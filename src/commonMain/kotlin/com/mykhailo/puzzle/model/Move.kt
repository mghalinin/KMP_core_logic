package com.mykhailo.puzzle.model

import kotlinx.serialization.Serializable

@Serializable
data class Move(
    val type: MoveType,
    val fromIndex: Int? = null,
    val toIndex: Int? = null,
    val newChar: Char? = null,
    val cost: Int
) {
    companion object {
        fun delete(index: Int, cost: Int = 1) = Move(
            type = MoveType.DELETE,
            fromIndex = index,
            cost = cost
        )
        
        fun move(from: Int, to: Int, cost: Int = 1) = Move(
            type = MoveType.MOVE,
            fromIndex = from,
            toIndex = to,
            cost = cost
        )
        
        fun exchange(index: Int, newChar: Char, cost: Int = 1) = Move(
            type = MoveType.EXCHANGE,
            fromIndex = index,
            newChar = newChar,
            cost = cost
        )
    }
}