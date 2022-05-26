package pt.ulusofona.deisi.cm2122.g21904825_21904341

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [FireRoom::class], version = 1)
abstract class FireDatabase : RoomDatabase() {

    abstract  fun fireDao() : FireDao

    companion object {
        private var instance: FireDatabase? = null

        fun getInstance(applicationContext: Context): FireDatabase {
            synchronized(this) {
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        applicationContext,
                        FireDatabase::class.java,
                        "fire_db"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
                }
                return instance as FireDatabase
            }
        }
    }
}