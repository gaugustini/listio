package com.gaugustini.listio.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A class used to represent each task.
 */
@Entity(tableName = "task")
data class Task(
    val title: String,
    val date: String,
    val hour: String,
    val description: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
