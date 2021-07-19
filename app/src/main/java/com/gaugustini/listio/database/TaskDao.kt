package com.gaugustini.listio.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for [Task].
 */
@Dao
interface TaskDao {

    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getTaskById(id: Long): Task

    @Query("SELECT * FROM task")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE date = :date")
    fun getTasksByDate(date: String): Flow<List<Task>>

}
