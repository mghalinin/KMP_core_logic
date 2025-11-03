package com.mykhailo.puzzle.sample

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertNotNull

class PuzzleGameSampleTest {
    
    @Test
    fun testSampleExecution() {
        val output = PuzzleGameSample.runSample()
        
        // Verify output contains expected elements
        assertTrue(output.contains("Puzzle Game Sample"))
        assertTrue(output.contains("hello"))
        assertTrue(output.contains("world"))
        assertTrue(output.contains("Complete: true"))
        assertTrue(output.contains("Cost:"))
    }
    
    @Test
    fun testConsistencyValidation() {
        val isConsistent = PuzzleGameSample.validateConsistency()
        assertTrue(isConsistent, "Cross-platform consistency validation should pass")
    }
    
    @Test
    fun testSampleOutputStructure() {
        val output = PuzzleGameSample.runSample()
        
        // Check for proper section headers
        assertTrue(output.contains("Sample 1:"))
        assertTrue(output.contains("Sample 2:"))
        assertTrue(output.contains("Sample 3:"))
        assertTrue(output.contains("Expert Analysis"))
        assertTrue(output.contains("Cost Validation"))
        
        // Verify all samples show progression
        assertTrue(output.contains("Initial:"))
        assertTrue(output.contains("Step"))
        assertTrue(output.contains("Delete:"))
        assertTrue(output.contains("Move:"))
    }
}