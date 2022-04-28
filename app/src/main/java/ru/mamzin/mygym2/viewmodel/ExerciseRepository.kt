package ru.mamzin.mygym2.viewmodel

import androidx.lifecycle.LiveData
import ru.mamzin.mygym2.database.ExerciseDao
import ru.mamzin.mygym2.model.Exercise

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    val allExercise: LiveData<MutableList<Exercise>> = exerciseDao.getExercises()

    suspend fun insert(exercise: Exercise) {
        exerciseDao.addExercise(exercise)
    }

    suspend fun delete(exercise: Exercise) {
        exerciseDao.deleteExercise(exercise)
    }

    suspend fun update(exercise: Exercise) {
        exerciseDao.updateExercise(exercise)
    }
}