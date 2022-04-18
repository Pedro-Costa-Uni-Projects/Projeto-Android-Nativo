package pt.ulusofona.deisi.cm2122.g21904825_21904341

import android.content.res.Resources
import java.text.SimpleDateFormat
import java.util.*

class Fire {

    var nome : String = "NaN"
    var cc : Int = 0

    var distrito : String = "NaN"
    var concelho : String = "NaN"
    var freguesia : String = "NaN"

    var operacionais : Int = 0
    var veiculos : Int = 0
    var aereos : Int = 0

    var estado : String = "NaN"
    var timestamp : Long = Date().time
    var observacoes : String = "NaN"
    var foto : String = "NaN"


    //Constructor API
    constructor(
        distrito: String,
        concelho: String,
        freguesia: String,
        operacionais: Int,
        veiculos: Int,
        aereos: Int,
        estado: String,
        timestamp: Long,
        observacoes: String,
        foto: String
    ) {
        this.distrito = distrito
        this.concelho = concelho
        this.freguesia = freguesia
        this.operacionais = operacionais
        this.veiculos = veiculos
        this.aereos = aereos
        this.estado = "Por Confirmar"
        this.timestamp = timestamp
        this.observacoes = observacoes
        this.foto = foto
    }


    //Constructor new fire registration
    constructor(nome: String, cc: Int, distrito: String, foto: String) {
        this.nome = nome
        this.cc = cc
        this.distrito = distrito
        this.foto = foto
        this.estado = "Por Confirmar"
    }

    fun data (): String {
        var formatter = SimpleDateFormat("dd/MM/yyyy - HH:mm:ss")
        var calender = Calendar.getInstance()
        calender.timeInMillis = this.timestamp
        return formatter.format(calender.time)
    }

    fun getState() : String {
        if (this.estado == "Por Confirmar") {
            return resourcesStatic!!.getString(R.string.to_be_confirmed)
        }
        return ""
    }




}