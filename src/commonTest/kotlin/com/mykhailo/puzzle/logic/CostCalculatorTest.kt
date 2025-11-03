package com.mykhailo.puzzle.logic

import com.mykhailo.puzzle.model.GameState
import com.mykhailo.puzzle.model.Move
import com.mykhailo.puzzle.model.MoveType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class CostCalculatorTest {
    
    @Test
    fun testDeleteCostCalculation() {
        val word = "hello"
        assertEquals(1, CostCalculator.calculateDeleteCost(word, 0))
        assertEquals(1, CostCalculator.calculateDeleteCost(word, 4))
        assertEquals(1, CostCalculator.calculateDeleteCost(word, 2))
    }
    
    @Test
    fun testDeleteCostInvalidIndex() {
        val word = "hello"
        assertFailsWith<IndexOutOfBoundsException> {
            CostCalculator.calculateDeleteCost(word, -1)
        }
        assertFailsWith<IndexOutOfBoundsException> {
            CostCalculator.calculateDeleteCost(word, 5)
        }
    }
    
    @Test
    fun testMoveCostCalculation() {
        val word = "hello"
        assertEquals(1, CostCalculator.calculateMoveCost(word, 0, 1))
        assertEquals(1, CostCalculator.calculateMoveCost(word, 4, 0))
        assertEquals(1, CostCalculator.calculateMoveCost(word, 2, 2))
    }
    
    @Test
    fun testExchangeCostCalculation() {
        val word = "hello"
        assertEquals(1, CostCalculator.calculateExchangeCost(word, 0, 'a'))
        assertEquals(1, CostCalculator.calculateExchangeCost(word, 4, 'z'))
    }
    
    @Test
    fun testTotalCostCalculation() {
        val moves = listOf(
            Move.delete(0, 1),
            Move.move(1, 2, 1),
            Move.exchange(3, 'x', 1)
        )
        assertEquals(3, CostCalculator.calculateTotalCost(moves))
    }
    
    @Test
    fun testEstimateMinimumCost() {
        assertEquals(0, CostCalculator.estimateMinimumCost("hello", "hello"))
        assertEquals(1, CostCalculator.estimateMinimumCost("hello", "helo"))
        assertEquals(2, CostCalculator.estimateMinimumCost("hello", "world"))
    }
}