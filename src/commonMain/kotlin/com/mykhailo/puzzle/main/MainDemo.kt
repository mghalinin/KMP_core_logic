package com.mykhailo.puzzle.main

import com.mykhailo.puzzle.validation.CrossPlatformValidator
import com.mykhailo.puzzle.sample.PuzzleGameSample

/**
 * Main demonstration and validation application.
 * This shows the shared core working and validates cross-platform consistency.
 */
object MainDemo {
    
    fun runDemo(): String {
        val output = StringBuilder()
        
        output.appendLine("=== Shared Puzzle Game Core - Cross-Platform Demo ===")
        output.appendLine("Author: Mykhailo Halinin <mykhailohalinin@gmail.com>")
        output.appendLine()
        
        // Run sample demonstrations
        output.appendLine("--- Running Sample Demonstrations ---")
        output.appendLine(PuzzleGameSample.runSample())
        
        // Run comprehensive validation
        output.appendLine("\n--- Running Cross-Platform Validation ---")
        val validationResult = CrossPlatformValidator.runAllValidations()
        
        validationResult.messages.forEach { message ->
            output.appendLine(message)
        }
        
        output.appendLine()
        output.appendLine("=== Summary ===")
        if (validationResult.success) {
            output.appendLine("Status: SUCCESS ✅")
            output.appendLine("The shared core is fully functional and ready for:")
            output.appendLine("• iOS Swift integration")
            output.appendLine("• Android Kotlin integration") 
            output.appendLine("• Identical gameplay behavior across platforms")
            output.appendLine("• Cost calculation accuracy")
            output.appendLine("• Expert-level analysis features")
        } else {
            output.appendLine("Status: FAILED ❌")
            output.appendLine("Please review the validation failures above.")
        }
        
        return output.toString()
    }
}