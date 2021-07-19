package com.gaugustini.listio.database

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    suspend fun insert(task: Task) = taskDao.insert(task)

    suspend fun update(task: Task) = taskDao.update(task)

    suspend fun delete(task: Task) = taskDao.delete(task)

    suspend fun getTaskById(id: Long) = taskDao.getTaskById(id)

    fun getTasks(): Flow<List<Task>> = taskDao.getTasks()

    fun getTasksByDate(date: String): Flow<List<Task>> = taskDao.getTasksByDate(date)

}
