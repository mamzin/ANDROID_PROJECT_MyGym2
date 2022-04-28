package ru.mamzin.mygym2.viewmodel

import androidx.lifecycle.LiveData
import ru.mamzin.mygym2.database.RecurrenceDao
import ru.mamzin.mygym2.model.Recurrence
import ru.mamzin.mygym2.model.RecurrenceAndExercise

class RecurrenceRepository(private val recurrenceDao: RecurrenceDao) {

    val allRecurrence: LiveData<MutableList<RecurrenceAndExercise>> = recurrenceDao.getStatistic()

    suspend fun insert(recurrence: Recurrence) {
        recurrenceDao.addSession(recurrence)
    }

    suspend fun delete(recurrence: Recurrence) {
        recurrenceDao.deleteRecurrence(recurrence)
    }
}