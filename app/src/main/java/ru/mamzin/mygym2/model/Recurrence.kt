package ru.mamzin.mygym2.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class Recurrence (
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val approach: Int,
    val repeat: Int,
    var date: Date = Date()
)