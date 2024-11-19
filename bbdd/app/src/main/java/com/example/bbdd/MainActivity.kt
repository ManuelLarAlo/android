package com.example.bbdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.compose.ui.semantics.text

class MainActivity : AppCompatActivity() {
    private lateinit var preguntaEditText: EditText
    private lateinit var respuesta1EditText: EditText
    private lateinit var respuesta2EditText: EditText
    private lateinit var respuesta3EditText: EditText
    private lateinit var respuesta4EditText: EditText
    private lateinit var esCorrectaEditText: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button

    private lateinit var sqliteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        sqliteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener { addPregunta() }
        btnView.setOnClickListener { getPreguntas() }
    }

    private fun getPreguntas() {
        val stdList = sqliteHelper.getAllPreguntas()
        Log.e("valor", "${stdList.size}")
        // Mostrar con un Recycleview
    }

    private fun addPregunta() {
        val pregunta = preguntaEditText.text.toString()
        val respuesta1 = respuesta1EditText.text.toString()
        val respuesta2 = respuesta2EditText.text.toString()
        val respuesta3 = respuesta3EditText.text.toString()
        val respuesta4 = respuesta4EditText.text.toString()
        val esCorrecta = esCorrectaEditText.text.toString()

        if (pregunta.isEmpty() || respuesta1.isEmpty() || respuesta2.isEmpty() || respuesta3.isEmpty() || respuesta4.isEmpty() || esCorrecta.isEmpty()) {
            Toast.makeText(this, "Inserta los valores obligatorios", Toast.LENGTH_LONG).show()
            return
        }

        val status = sqliteHelper.insertPreguntas(pregunta, respuesta1, respuesta2, respuesta3, respuesta4, esCorrecta)

        if (status > -1) {
            Toast.makeText(this, "Insertado correctamente", Toast.LENGTH_LONG).show()
            clearEditText()
        } else {
            Toast.makeText(this, "Registro no guardado", Toast.LENGTH_LONG).show()
        }
    }

    private fun clearEditText() {
        preguntaEditText.setText("")
        respuesta1EditText.setText("")
        respuesta2EditText.setText("")
        respuesta3EditText.setText("")
        respuesta4EditText.setText("")
        esCorrectaEditText.setText("")
        preguntaEditText.requestFocus()
    }

    private fun initView() {
        preguntaEditText = findViewById(R.id.pregunta) // Assuming you have these IDs in your layout
        respuesta1EditText = findViewById(R.id.respuesta1)
        respuesta2EditText = findViewById(R.id.respuesta2)
        respuesta3EditText = findViewById(R.id.respuesta3)
        respuesta4EditText = findViewById(R.id.respuesta4)
        esCorrectaEditText = findViewById(R.id.esCorrecta)
        btnAdd = findViewById(R.id.btnInsertar)
        btnView = findViewById(R.id.btnView)
    }
}