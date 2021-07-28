package com.gaugustini.listio.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gaugustini.listio.model.Task

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

}
