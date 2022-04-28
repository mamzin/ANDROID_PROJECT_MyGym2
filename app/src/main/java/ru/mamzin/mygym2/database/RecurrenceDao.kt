package ru.mamzin.mygym2.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.mamzin.mygym2.model.Exercise
import ru.mamzin.mygym2.model.Recurrence

@Dao
interface RecurrenceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSession(recurrence: Recurrence)

    @Query("SELECT * FROM Recurrence ORDER BY date ASC")
    fun getRecurrence(): LiveData<MutableList<Recurrence>>

}