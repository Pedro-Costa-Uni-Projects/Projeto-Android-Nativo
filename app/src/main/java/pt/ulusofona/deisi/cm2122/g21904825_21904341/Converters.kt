package pt.ulusofona.deisi.cm2122.g21904825_21904341

import java.text.SimpleDateFormat
import java.util.*

fun getData (time : Long ): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy - HH:mm:ss")
    val calender = Calendar.getInstance()
    calender.timeInMillis = time
    return formatter.format(calender.time)
}