package ru.mamzin.mygym2.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Exercise(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val name: String,
    val description: String,
    val photo: Bitmap?
)