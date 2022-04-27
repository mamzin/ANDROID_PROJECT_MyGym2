package ru.mamzin.mygym2.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.mamzin.mygym2.model.Exercise
import java.util.*

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM Exercise ORDER BY name ASC")
    fun getExercises(): LiveData<MutableList<Exercise>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addExercise(exercise: Exercise)

    @Update
    suspend fun updateExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)
}