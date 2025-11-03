package com.mykhailo.puzzle.model

import kotlinx.serialization.Serializable

@Serializable
enum class MoveType {
    DELETE,
    MOVE, 
    EXCHANGE
}