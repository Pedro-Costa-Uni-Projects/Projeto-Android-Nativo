package pt.ulusofona.deisi.cm2122.g21904825_21904341.apiModels

data class ActiveFiresData(
    val district: String,
    val concelho: String,
    val freguesia: String,
    val man: Int,
    val terrain: Int,
    val aerial: Int,
    val status: String,
    val dateTime : DateTime,
    val natureza: String,
    val lat: Double,
    val lng: Double,

)
