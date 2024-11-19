package com.example.bbdd

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class SQLiteHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME ="preguntas.db"
        private const val  TBL_PREGUNTAS = "tbl_preguntas"
        private const val ID = "id"
        private const val PREGUNTA = "pregunta"
        private const val RESPUESTA1 = "respuesta1"
        private const val RESPUESTA2 = "respuesta2"
        private const val RESPUESTA3 = "respuesta3"
        private const val RESPUESTA4 = "respuesta4"
        private const val ESCORRECTA = "esCorrecta"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblPreguntas = ("CREATE TABLE "+ TBL_PREGUNTAS+"("
                + ID+ " INTEGER PRIMARY KEY,"+ PREGUNTA+ " TEXT,"
                + RESPUESTA1+ " TEXT,"+ RESPUESTA2+ " TEXT,"+ RESPUESTA3+ " TEXT,"+ RESPUESTA4+ " TEXT,"+ ESCORRECTA+ " TEXT,"+")"
                )
        //Ejecutamos la setencia
        db?.execSQL(createTblPreguntas)
    }

    override fun onUpgrade(db: SQLiteDatabase?, old: Int, new: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_PREGUNTAS")
        onCreate(db)
    }

    fun insertPreguntas(std: PreguntaModel): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(PREGUNTA, std.pregunta)
        contentValues.put(RESPUESTA1, std.respuesta1)
        contentValues.put(RESPUESTA2, std.respuesta2)
        contentValues.put(RESPUESTA3, std.respuesta3)
        contentValues.put(RESPUESTA4, std.respuesta4)
        contentValues.put(ESCORRECTA, std.esCorrecta)

        val sucess = db.insert(TBL_PREGUNTAS, null, contentValues)
        db.close()
        return sucess

    }

    @SuppressLint("Range")
    fun getAllPreguntas () : ArrayList<PreguntaModel>{
        val stdList: ArrayList<PreguntaModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_PREGUNTAS"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
           cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var pregunta: String
        var respuesta1: String
        var respuesta2: String
        var respuesta3: String
        var respuesta4: String
        var esCorrecta: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                pregunta = cursor.getString(cursor.getColumnIndex("pregunta"))
                respuesta1 = cursor.getString(cursor.getColumnIndex("respuesta1"))
                respuesta2 = cursor.getString(cursor.getColumnIndex("respuesta2"))
                respuesta3 = cursor.getString(cursor.getColumnIndex("respuesta3"))
                respuesta4 = cursor.getString(cursor.getColumnIndex("respuesta4"))
                esCorrecta = cursor.getString(cursor.getColumnIndex("esCorrecta"))

                val std = PreguntaModel(id = id, pregunta = pregunta, respuesta1 = respuesta1,
                    respuesta2 = respuesta2, respuesta3 = respuesta3, respuesta4 = respuesta4, esCorrecta = esCorrecta)
                stdList.add(std)
            } while (cursor.moveToNext())
        }
        return stdList

    }
}