package ru.mamzin.mygym2.model

import java.util.*

data class RecurrenceAndExercise(
    val id: UUID,
    val recurrence_id: UUID,
    val name: String,
    val session: Int,
    val repeat: Int,
    var date: String
)
