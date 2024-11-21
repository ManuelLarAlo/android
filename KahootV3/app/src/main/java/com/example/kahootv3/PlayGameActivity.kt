package com.example.kahootv3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PlayGameActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private lateinit var questionList: List<Question>
    private var currentQuestionIndex = 0
    private var correctAnswers = 0
    private var wrongAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_game)

        val tvQuestion: TextView = findViewById(R.id.tv_question)
        val btnAnswer1: Button = findViewById(R.id.btn_answer1)
        val btnAnswer2: Button = findViewById(R.id.btn_answer2)
        val btnAnswer3: Button = findViewById(R.id.btn_answer3)
        val btnAnswer4: Button = findViewById(R.id.btn_answer4)
        val btnContinue: Button = findViewById(R.id.btn_continue)

        dbHelper = DBHelper(this)
        questionList = dbHelper.getQuestions()

        if (questionList.size < 8) {
            Toast.makeText(this, "Debe haber al menos 8 preguntas para jugar.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        questionList = questionList.shuffled().take(5)
        showQuestion(tvQuestion, btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4)

        var selectedAnswer = -1

        btnAnswer1.setOnClickListener { selectedAnswer = 0 }
        btnAnswer2.setOnClickListener { selectedAnswer = 1 }
        btnAnswer3.setOnClickListener { selectedAnswer = 2 }
        btnAnswer4.setOnClickListener { selectedAnswer = 3 }

        btnContinue.setOnClickListener {
            if (selectedAnswer == -1) {
                Toast.makeText(this, "Seleccione una respuesta.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val correctIndex = questionList[currentQuestionIndex].correctAnswerIndex
            if (selectedAnswer == correctIndex) correctAnswers++ else wrongAnswers++

            currentQuestionIndex++
            if (currentQuestionIndex < questionList.size) {
                selectedAnswer = -1
                showQuestion(tvQuestion, btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4)
            } else {
                val intent = Intent(this, SummaryActivity::class.java)
                intent.putExtra("TOTAL_QUESTIONS", questionList.size)
                intent.putExtra("CORRECT_ANSWERS", correctAnswers)
                intent.putExtra("WRONG_ANSWERS", wrongAnswers)
                startActivity(intent)
                finish()
            }
        }

    }

    private fun showQuestion(tvQuestion: TextView, btn1: Button, btn2: Button, btn3: Button, btn4: Button) {
        val question = questionList[currentQuestionIndex]
        tvQuestion.text = question.text
        btn1.text = question.answers[0]
        btn2.text = question.answers[1]
        btn3.text = question.answers[2]
        btn4.text = question.answers[3]
    }
}

