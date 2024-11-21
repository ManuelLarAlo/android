package com.example.kahootv3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playButton: Button = findViewById(R.id.btn_play_game)
        val summaryButton: Button = findViewById(R.id.btn_summary)
        val addQuestionButton: Button = findViewById(R.id.btn_add_question)

        playButton.setOnClickListener {
            val intent = Intent(this, PlayGameActivity::class.java)
            startActivity(intent)
        }

        summaryButton.setOnClickListener {
            val intent = Intent(this, SummaryActivity::class.java)
            startActivity(intent)
        }

        addQuestionButton.setOnClickListener {
            val intent = Intent(this, AddQuestionActivity::class.java)
            startActivity(intent)
        }
    }
}
