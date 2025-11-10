package com.ubaya.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import buildDb
import com.ubaya.todoapp.model.Todo
import com.ubaya.todoapp.model.TodoDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    private val job = Job()

    fun addTodo(todo: Todo) {
        launch { // di dalam Coroutine bukan menggunakan .value tapi .postValue
            val db = buildDb(getApplication())
            db.todoDao().insertAll(todo)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}