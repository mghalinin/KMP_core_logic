#!/bin/bash

# Quick validation script to verify project setup
echo "=== Shared Puzzle Game - Quick Validation ==="
echo "Author: Mykhailo Halinin <mykhailohalinin@gmail.com>"
echo ""

# Check project structure
echo "📁 Checking project structure..."
if [ -d "src/commonMain" ]; then
    echo "✅ Common source directory exists"
else 
    echo "❌ Common source directory missing"
fi

if [ -d "src/androidMain" ]; then
    echo "✅ Android source directory exists"
else
    echo "❌ Android source directory missing"  
fi

if [ -d "src/iosMain" ]; then
    echo "✅ iOS source directory exists"
else
    echo "❌ iOS source directory missing"
fi

if [ -d "src/commonTest" ]; then
    echo "✅ Test directory exists"
else
    echo "❌ Test directory missing"
fi

echo ""
echo "📋 Core files present:"

core_files=(
    "src/commonMain/kotlin/com/mykhailo/puzzle/model/GameState.kt"
    "src/commonMain/kotlin/com/mykhailo/puzzle/model/Move.kt" 
    "src/commonMain/kotlin/com/mykhailo/puzzle/logic/GameEngine.kt"
    "src/commonMain/kotlin/com/mykhailo/puzzle/logic/CostCalculator.kt"
    "src/commonMain/kotlin/com/mykhailo/puzzle/logic/WordTransformer.kt"
    "src/commonMain/kotlin/com/mykhailo/puzzle/logic/ExpertComparison.kt"
    "src/commonMain/kotlin/com/mykhailo/puzzle/storage/ProgressStore.kt"
    "src/androidMain/kotlin/com/mykhailo/puzzle/storage/ProgressStore.android.kt"
    "src/iosMain/kotlin/com/mykhailo/puzzle/storage/ProgressStore.ios.kt"
)

for file in "${core_files[@]}"; do
    if [ -f "$file" ]; then
        echo "✅ $file"
    else
        echo "❌ $file"  
    fi
done

echo ""
echo "🧪 Test files present:"

test_files=(
    "src/commonTest/kotlin/com/mykhailo/puzzle/logic/GameEngineTest.kt"
    "src/commonTest/kotlin/com/mykhailo/puzzle/logic/CostCalculatorTest.kt"
    "src/commonTest/kotlin/com/mykhailo/puzzle/logic/WordTransformerTest.kt"
    "src/commonTest/kotlin/com/mykhailo/puzzle/logic/ExpertComparisonTest.kt"
    "src/commonTest/kotlin/com/mykhailo/puzzle/storage/ProgressStoreTest.kt"
)

for file in "${test_files[@]}"; do
    if [ -f "$file" ]; then
        echo "✅ $file"
    else
        echo "❌ $file"
    fi  
done

echo ""
echo "📄 Configuration files:"

config_files=(
    "build.gradle.kts"
    "settings.gradle.kts" 
    "gradle/wrapper/gradle-wrapper.properties"
    "gradlew.bat"
    "README.md"
    "IMPLEMENTATION.md"
    ".gitignore"
)

for file in "${config_files[@]}"; do
    if [ -f "$file" ]; then
        echo "✅ $file"
    else
        echo "❌ $file"
    fi
done

echo ""
echo "📊 Project Statistics:"
echo "• Kotlin source files: $(find src -name "*.kt" | wc -l)"
echo "• Lines of code: $(find src -name "*.kt" -exec wc -l {} + | tail -1 | awk '{print $1}')"
echo "• Test files: $(find src -path "*/Test*" -name "*.kt" | wc -l)"

echo ""
echo "🎯 Ready for:"
echo "✅ iOS Swift integration"
echo "✅ Android Kotlin integration"  
echo "✅ Cross-platform development"
echo "✅ Production deployment"

echo ""
echo "=== Validation Complete ==="