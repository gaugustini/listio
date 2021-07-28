package com.gaugustini.listio.ui.entry

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.gaugustini.listio.model.Task
import com.gaugustini.listio.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskEntryViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
) : ViewModel() {

    private var task: LiveData<Task>? = null

    fun getTask(id: Long): LiveData<Task> {
        return task ?: liveData {
            emit(taskRepository.getTaskById(id))
        }.also {
            task = it
        }
    }

    fun isEntryValid(title: String, hour: String, date: String): Boolean {
        if (title.isBlank() || hour.isBlank() || date.isBlank()) {
            return false
        }
        return true
    }

    fun entryTask(
        id: Long,
        title: String,
        date: String,
        hour: String,
        description: String
    ) {
        viewModelScope.launch {
            if (id > 0) {
                update(Task(title, date, hour, description, id))
            } else {
                insert(Task(title, date, hour, description))
            }
        }
    }

    private suspend fun insert(task: Task) {
        taskRepository.insert(task)
    }

    private suspend fun update(task: Task) {
        taskRepository.update(task)
    }
}
