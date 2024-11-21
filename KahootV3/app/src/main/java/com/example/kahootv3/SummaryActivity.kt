package com.example.kahootv3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        val totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 0)
        val correctAnswers = intent.getIntExtra("CORRECT_ANSWERS", 0)
        val wrongAnswers = intent.getIntExtra("WRONG_ANSWERS", 0)

        val tvTotalQuestions: TextView = findViewById(R.id.tv_total_questions)
        val tvCorrectAnswers: TextView = findViewById(R.id.tv_correct_answers)
        val tvWrongAnswers: TextView = findViewById(R.id.tv_wrong_answers)
        val btnReturnMain: Button = findViewById(R.id.btn_return_main)

        tvTotalQuestions.text = "Total de preguntas: $totalQuestions"
        tvCorrectAnswers.text = "Número de aciertos: $correctAnswers"
        tvWrongAnswers.text = "Número de fallos: $wrongAnswers"

        btnReturnMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}
