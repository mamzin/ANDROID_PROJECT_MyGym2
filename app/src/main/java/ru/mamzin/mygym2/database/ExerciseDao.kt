package ru.mamzin.mygym2.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.mamzin.mygym2.model.Exercise

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM Exercise")
    fun getExercises(): LiveData<MutableList<Exercise>>

    @Query("SELECT * FROM Exercise WHERE id = :id ")
    suspend fun loadSingle(id: Int): MutableList<Exercise>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addExercise(exercise: Exercise)

    @Update
    suspend fun updateExercise(vararg exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)
}