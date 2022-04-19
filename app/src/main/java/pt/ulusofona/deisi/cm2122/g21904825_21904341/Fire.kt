package pt.ulusofona.deisi.cm2122.g21904825_21904341

import java.text.SimpleDateFormat
import java.util.*

class Fire {

    private var name : String = "NaN"
    private var cc : Int = 0

    private var district : String = "NaN"
    private var county : String = "NaN"
    private var parish : String = "NaN"

    private var operational : Int = 0
    private var vehicles : Int = 0
    private var aerial : Int = 0

    private var state : String = "NaN"
    private var timestamp : Long = Date().time
    private var comments : String = "NaN"
    private var photo : String = "NaN"


    //Constructor API
    constructor(
        district: String,
        county: String,
        parish: String,
        operational: Int,
        vehicles: Int,
        aerial: Int,
        state: String,
        timestamp: Long,
        comments: String,
        photo: String
    ) {
        this.district = district
        this.county = county
        this.parish = parish
        this.operational = operational
        this.vehicles = vehicles
        this.aerial = aerial
        this.state = state
        this.timestamp = timestamp
        this.comments = comments
        this.photo = photo
    }


    //Constructor new fire registration
    constructor(name: String, cc: Int, district: String, foto: String) {
        this.name = name
        this.cc = cc
        this.district = district
        this.photo = photo
        this.state = "Por Confirmar"
    }

    fun getData (): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy - HH:mm:ss")
        val calender = Calendar.getInstance()
        calender.timeInMillis = this.timestamp
        return formatter.format(calender.time)
    }

    fun getState() : String {
        if (this.state == "Por Confirmar") {
            return resourcesStatic!!.getString(R.string.to_be_confirmed)
        }
        return ""
    }

    fun getName() : String {
        if (this.name == "NaN") {
            return resourcesStatic!!.getString(R.string.info_not_available)
        }
        return this.name
    }

    fun getCC() : String {
        if (this.cc == 0) {
            return resourcesStatic!!.getString(R.string.info_not_available)
        }
        return this.cc.toString()
    }

    fun getDistrict() : String {
        return this.district
    }

    fun getCounty() : String {
        return this.county
    }

    fun getParish() : String {
        return this.parish
    }

    fun getOperational() : String {
        return this.operational.toString()
    }

    fun getVehicles() : String {
        return this.vehicles.toString()
    }

    fun getAerial() : String {
        return this.aerial.toString()
    }

    fun getComments() : String {
        return this.comments
    }

    fun getPhoto() : String {
        return this.photo
    }

}