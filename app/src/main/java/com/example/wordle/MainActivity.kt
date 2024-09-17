package com.example.wordle

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    var randomWord: String = ""
    var guessCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // make a random word when the game starts
        randomWord = FourLetterWordList.getRandomFourLetterWord()
        // log to check a random word is created
        Log.d("Wordle", "Random word generated: $randomWord")

        val inputField = findViewById<EditText>(R.id.et_user_input)
        val submitButton = findViewById<Button>(R.id.btn_submit)
        val resultTextView = findViewById<TextView>(R.id.tv_result)
        val resetButton = findViewById<Button>(R.id.btn_reset)
        val answerTextView = findViewById<TextView>(R.id.tv_answer)

        submitButton.setOnClickListener {
            val userInput = inputField.text.toString().uppercase()

            if(userInput.length == 4) {
                guessCount++
                val result = checkGuess(userInput, randomWord)
                resultTextView.text = result

                if(userInput == randomWord) {
                    findViewById<ConstraintLayout>(R.id.main).setBackgroundColor(
                        ContextCompat.getColor(this, android.R.color.holo_green_light)
                    )
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
                }

                if (guessCount >= 3) {
                    submitButton.isEnabled = false
                    Toast.makeText(this, "You have exceeded the number of attempts!", Toast.LENGTH_SHORT).show()
                    answerTextView.text = "Answer: $randomWord"
                }
            } else {
                Toast.makeText(this, "Enter a four-letter word", Toast.LENGTH_SHORT).show()
            }
        }

        resetButton.setOnClickListener {
            randomWord = FourLetterWordList.getRandomFourLetterWord()
            guessCount = 0
            submitButton.isEnabled = true
            inputField.text.clear()
            resultTextView.text = ""
            Toast.makeText(this, "The game has been reset", Toast.LENGTH_SHORT).show()
            Log.d("Wordle", "New random word generated: $randomWord")
        }
    }
}