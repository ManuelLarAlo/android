package com.example.bbdd

import kotlin.random.Random

data class PreguntaModel(
    var id: Int = getAutoId(),
    var pregunta: String = "",
    var respuesta1: String = "",
    var respuesta2: String = "",
    var respuesta3: String = "",
    var respuesta4: String = "",
    var esCorrecta: String = "",


){
    companion object {
        fun getAutoId():Int {
            val random = Random
            return  random.nextInt(100)
        }
    }
}