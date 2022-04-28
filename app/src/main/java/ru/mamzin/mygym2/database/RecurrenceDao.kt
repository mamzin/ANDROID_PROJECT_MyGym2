package ru.mamzin.mygym2.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.mamzin.mygym2.model.Recurrence
import ru.mamzin.mygym2.model.RecurrenceAndExercise

@Dao
interface RecurrenceDao {

    @Query(
        "SELECT Exercise.id, Recurrence.recurrence_id, Exercise.name, Recurrence.session, Recurrence.repeat, Recurrence.date " +
                "FROM Exercise INNER JOIN Recurrence ON Exercise.id = Recurrence.exercise_id ORDER BY Recurrence.date ASC"
    )
    fun getStatistic(): LiveData<MutableList<RecurrenceAndExercise>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSession(recurrence: Recurrence)

    @Delete
    suspend fun deleteRecurrence(recurrence: Recurrence)

}