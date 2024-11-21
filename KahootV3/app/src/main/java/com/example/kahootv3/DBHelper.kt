package com.example.kahootv3

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Definimos un data class para representar una pregunta con sus respuestas
data class Question(val text: String, val answers: List<String>, val correctAnswerIndex: Int)

// Clase que gestiona la base de datos SQLite
class DBHelper(context: Context) : SQLiteOpenHelper(context, "kahoot.db", null, 1) {

    // Se ejecuta cuando la base de datos se crea por primera vez
    override fun onCreate(db: SQLiteDatabase) {
        // Creamos la tabla 'questions' con las columnas necesarias para almacenar las preguntas y respuestas
        db.execSQL(
            "CREATE TABLE questions (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +   // id es la clave primaria, se incrementa automáticamente
                    "text TEXT, " +                              // La pregunta como texto
                    "answer1 TEXT, " +                           // Respuesta 1
                    "answer2 TEXT, " +                           // Respuesta 2
                    "answer3 TEXT, " +                           // Respuesta 3
                    "answer4 TEXT, " +                           // Respuesta 4
                    "correctAnswerIndex INTEGER" +               // Índice de la respuesta correcta (0 a 3)
            ")"
        )
    }

    // Se ejecuta cuando hay una actualización de la base de datos (por ejemplo, si cambiamos la estructura de la tabla)
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Si la base de datos ya existe y tiene una versión diferente, eliminamos la tabla antigua
        db.execSQL("DROP TABLE IF EXISTS questions")
        // Luego creamos la nueva tabla
        onCreate(db)
    }

    // Método para insertar una nueva pregunta en la base de datos
    fun insertQuestion(question: Question) {
        // Abrimos la base de datos en modo escritura
        writableDatabase.execSQL(
            "INSERT INTO questions (text, answer1, answer2, answer3, answer4, correctAnswerIndex) VALUES (?, ?, ?, ?, ?, ?)",
            arrayOf(                                // Pasamos los valores de la pregunta y respuestas
                question.text,                      // El texto de la pregunta
                question.answers[0],                // Respuesta 1
                question.answers[1],                // Respuesta 2
                question.answers[2],                // Respuesta 3
                question.answers[3],                // Respuesta 4
                question.correctAnswerIndex         // El índice de la respuesta correcta
            )
        )
    }

    // Método para obtener todas las preguntas almacenadas en la base de datos
    fun getQuestions(): List<Question> {
        // Realizamos una consulta para obtener todas las preguntas de la tabla 'questions'
        val cursor = readableDatabase.rawQuery("SELECT * FROM questions", null)

        // Creamos una lista para almacenar las preguntas
        val questions = mutableListOf<Question>()

        // Iteramos sobre los resultados de la consulta
        while (cursor.moveToNext()) {
            // Creamos una nueva instancia de 'Question' a partir de los datos del cursor
            questions.add(
                Question(
                    cursor.getString(1),  // 'text' - la pregunta (columna 1)
                    listOf(
                        cursor.getString(2),  // 'answer1' (columna 2)
                        cursor.getString(3),  // 'answer2' (columna 3)
                        cursor.getString(4),  // 'answer3' (columna 4)
                        cursor.getString(5)   // 'answer4' (columna 5)
                    ),
                    cursor.getInt(6)  // 'correctAnswerIndex' (columna 6) - índice de la respuesta correcta
                )
            )
        }

        // Cerramos el cursor para liberar recursos
        cursor.close()

        // Devolvemos la lista de preguntas
        return questions
    }
}
