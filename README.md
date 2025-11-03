# Shared Puzzle Game - Kotlin Multiplatform

A cross-platform word puzzle game engine built with Kotlin Multiplatform, providing identical gameplay logic for both iOS and Android platforms.

## Features

- **Shared Game Logic**: Centralized puzzle mechanics for consistent gameplay across platforms
- **Cost Calculation**: Intelligent scoring system for different move types (delete, move, exchange)
- **Expert Analysis**: Advanced feedback and solution optimization
- **Cross-Platform**: Single codebase targeting iOS and Android
- **Comprehensive Testing**: Full test coverage for all game logic

## Architecture

```
/shared
  ├── src/commonMain/kotlin/
  │      ├── model/          # Data models (GameState, Move, etc.)
  │      ├── logic/          # Core game logic and algorithms  
  │      └── storage/        # Progress persistence
  ├── src/androidMain/       # Android-specific implementations
  ├── src/iosMain/           # iOS-specific implementations
  └── src/commonTest/        # Shared tests
```

## Core Functions

- `deleteLetter(state: GameState, index: Int): GameState`
- `moveLetter(state: GameState, from: Int, to: Int): GameState`
- `exchangeLetter(state: GameState, index: Int, newChar: Char): GameState`
- `calculateCost(state: GameState): Int`
- `checkWin(state: GameState): Boolean`

## Data Models

### GameState
```kotlin
data class GameState(
    val currentWord: String,
    val targetWord: String,
    val totalCost: Int,
    val moves: List<Move>
)
```

### Move
```kotlin
data class Move(
    val type: MoveType,
    val fromIndex: Int? = null,
    val toIndex: Int? = null,
    val newChar: Char? = null,
    val cost: Int
)
```

## Building

### Prerequisites
- JDK 8 or higher
- Android SDK (for Android target)
- Xcode (for iOS target)

### Build Commands
```bash
# Build all targets
./gradlew build

# Run tests
./gradlew test

# Android library
./gradlew assembleAndroid

# iOS framework
./gradlew linkDebugFrameworkIos
```

## Integration

### Android (Kotlin)
```kotlin
val gameEngine = GameEngine
val state = gameEngine.createGame("hello", "world")
val newState = gameEngine.exchangeLetter(state, 0, 'w')
```

### iOS (Swift)
```swift
let gameEngine = GameEngine()
let state = gameEngine.createGame(startWord: "hello", targetWord: "world")
let newState = gameEngine.exchangeLetter(state: state, index: 0, newChar: "w")
```

## Testing

Run the comprehensive test suite:
```bash
./gradlew test
```

The tests cover:
- Cost calculation accuracy
- Word transformation correctness
- Game state validation
- Expert analysis algorithms
- Cross-platform serialization

## License

MIT License - see LICENSE file for details.

## Author

**Mykhailo Halinin**
- Email: mykhailohalinin@gmail.com
- GitHub: [@mykhailo-halinin](https://github.com/mghalinin)