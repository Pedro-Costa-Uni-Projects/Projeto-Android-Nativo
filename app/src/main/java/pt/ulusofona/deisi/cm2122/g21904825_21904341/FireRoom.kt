package pt.ulusofona.deisi.cm2122.g21904825_21904341

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fire")
data class FireRoom (

    @ColumnInfo (name= "is_api") val isFromAPI: Boolean,
    @ColumnInfo (name = "name") val name: String,
    @ColumnInfo (name = "cc") val cc: Int,
    @ColumnInfo (name = "district") val district: String,
    @ColumnInfo (name = "county") val county: String,
    @ColumnInfo (name = "parish") val parish: String,
    @ColumnInfo (name = "operational") val operational: Int,
    @ColumnInfo (name = "vehicles") val vehicles: Int,
    @ColumnInfo (name = "aerial") val aerial: Int,
    @ColumnInfo (name = "state") val state: String,
    @ColumnInfo (name = "timestamp") val timestamp: Long,
    @ColumnInfo (name = "comments") val comments: String,
    @ColumnInfo (name = "photo") val photo: String?,
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}