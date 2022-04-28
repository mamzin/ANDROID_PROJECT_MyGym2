package ru.mamzin.mygym2.viewmodel

import androidx.lifecycle.LiveData
import ru.mamzin.mygym2.database.RecurrenceDao
import ru.mamzin.mygym2.model.Exercise
import ru.mamzin.mygym2.model.Recurrence

class RecurrenceRepository(private val recurrenceDao: RecurrenceDao) {

    val allRecurrence: LiveData<MutableList<Recurrence>> = recurrenceDao.getRecurrence()

    suspend fun insert(recurrence: Recurrence) {
        recurrenceDao.addSession(recurrence)
    }
}