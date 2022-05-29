package pt.ulusofona.deisi.cm2122.g21904825_21904341

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FireDao {
    @Insert
    suspend fun insert(fire: FireRoom)

    @Query("SELECT * FROM fire ORDER BY timestamp ASC")
    suspend fun getAll(): List<FireRoom>

    @Query("DELETE FROM fire where is_api= :isFromAPI")
    suspend fun purgeDB(isFromAPI : Boolean)

    @Query("SELECT * FROM fire WHERE id = :id")
    suspend fun getById(id: Int) : FireRoom
}