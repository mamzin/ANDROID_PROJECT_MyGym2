package ru.mamzin.mygym2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.mamzin.mygym2.database.AppDataBase
import ru.mamzin.mygym2.model.Recurrence
import ru.mamzin.mygym2.model.RecurrenceAndExercise

class RecurrenceViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecurrenceRepository
    val allRecurrence: LiveData<MutableList<RecurrenceAndExercise>>

    init {
        val dao = AppDataBase.getDatabase(application).recurrenceDao()
        repository = RecurrenceRepository(dao)
        allRecurrence = repository.allRecurrence
    }

    fun addRecurrence(recurrence: Recurrence) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(recurrence)
    }

    fun deleteRecurrence(recurrence: Recurrence) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(recurrence)
    }

}