package ru.mamzin.mygym2.model

import androidx.room.Entity
import androidx.room.ForeignKey
import java.util.*

@Entity(
    primaryKeys = ["exercise_id", "recurrence_id"],
    foreignKeys = [ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )]
)
class Recurrence (
    val exercise_id: UUID,
    val recurrence_id: UUID = UUID.randomUUID(),
    val session: Int,
    val repeat: Int,
    var date: String
)