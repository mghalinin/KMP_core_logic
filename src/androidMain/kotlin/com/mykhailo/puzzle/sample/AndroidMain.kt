package com.mykhailo.puzzle.sample

import com.mykhailo.puzzle.platform.getPlatform

fun main() {
    println("Platform: ${getPlatform().name}")
    println()
    
    // Run the sample demonstration
    val output = PuzzleGameSample.runSample()
    println(output)
    
    // Validate consistency
    val isConsistent = PuzzleGameSample.validateConsistency()
    println("=== Validation Result ===")
    println("Cross-platform consistency: ${if (isConsistent) "PASSED" else "FAILED"}")
    
    if (isConsistent) {
        println("✅ All core functions working correctly!")
        println("✅ Shared logic validated successfully!")
        println("✅ Ready for iOS/Android integration!")
    } else {
        println("❌ Validation failed - check implementation")
    }
}