package com.mykhailo.puzzle.model

import kotlinx.serialization.Serializable

@Serializable
data class LetterResult(
    val position: Int,
    val letter: Char,
    val status: LetterStatus
)

@Serializable
enum class LetterStatus {
    CORRECT,    // Letter is in correct position
    PRESENT,    // Letter exists in target but wrong position
    ABSENT      // Letter doesn't exist in target
}