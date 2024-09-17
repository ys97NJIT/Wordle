package com.example.wordle

fun checkGuess(guess: String, wordToGuess: String): String {
    var result = ""
    for (i in 0..3) {
        if (guess[i] == wordToGuess[i]) {
            result += "O"
        } else if (guess[i] in wordToGuess) {
            result += "+"
        } else {
            result += "X"
        }
    }
    return result
}