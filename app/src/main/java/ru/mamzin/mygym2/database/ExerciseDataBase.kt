package ru.mamzin.mygym2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.mamzin.mygym2.model.Exercise

@Database(entities = [Exercise::class], version = 1)
@TypeConverters(Converters::class)
abstract class ExerciseDataBase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao

    companion object {
        @Volatile
        private var INSTANCE: ExerciseDataBase? = null

        fun getDatabase(context: Context): ExerciseDataBase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): ExerciseDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                ExerciseDataBase::class.java,
                "database"
            ).build()
        }
    }
}