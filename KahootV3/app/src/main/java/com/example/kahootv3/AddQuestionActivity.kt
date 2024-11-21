package com.example.kahootv3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddQuestionActivity : AppCompatActivity() {

    private var correctAnswerIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_question)

        // Referencias a los elementos de la UI
        val etQuestion: EditText = findViewById(R.id.et_question)
        val etAnswer1: EditText = findViewById(R.id.et_answer1)
        val etAnswer2: EditText = findViewById(R.id.et_answer2)
        val etAnswer3: EditText = findViewById(R.id.et_answer3)
        val etAnswer4: EditText = findViewById(R.id.et_answer4)

        val btnCorrect1: Button = findViewById(R.id.btn_correct1)
        val btnCorrect2: Button = findViewById(R.id.btn_correct2)
        val btnCorrect3: Button = findViewById(R.id.btn_correct3)
        val btnCorrect4: Button = findViewById(R.id.btn_correct4)
        val btnSave: Button = findViewById(R.id.btn_save)

        // Instancia del DBHelper
        val dbHelper = DBHelper(this)

        // Asignar la respuesta correcta
        btnCorrect1.setOnClickListener { correctAnswerIndex = 0 }
        btnCorrect2.setOnClickListener { correctAnswerIndex = 1 }
        btnCorrect3.setOnClickListener { correctAnswerIndex = 2 }
        btnCorrect4.setOnClickListener { correctAnswerIndex = 3 }

        // Guardar la pregunta y las respuestas
        btnSave.setOnClickListener {
            val question = etQuestion.text.toString().trim()
            val answers = listOf(
                etAnswer1.text.toString().trim(),
                etAnswer2.text.toString().trim(),
                etAnswer3.text.toString().trim(),
                etAnswer4.text.toString().trim()
            )

            // Validar que todos los campos estén completos
            if (question.isEmpty() || answers.any { it.isEmpty() } || correctAnswerIndex == -1) {
                Toast.makeText(this, "Rellena todos los campos y selecciona una respuesta correcta", Toast.LENGTH_SHORT).show()
            } else {
                // Crear un objeto de tipo Question
                val newQuestion = Question(question, answers, correctAnswerIndex)

                // Insertar la pregunta en la base de datos
                dbHelper.insertQuestion(newQuestion)

                // Mostrar mensaje de éxito y cerrar la actividad
                Toast.makeText(this, "Pregunta guardada correctamente", Toast.LENGTH_SHORT).show()
                finish() // Volver a la actividad anterior
            }
        }
    }
}
