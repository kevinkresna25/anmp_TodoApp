package com.ubaya.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import buildDb
import com.ubaya.todoapp.model.Todo
import com.ubaya.todoapp.model.TodoDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListTodoViewModel(application: Application):AndroidViewModel(application), CoroutineScope {
    val todoLD = MutableLiveData<List<Todo>>()
    val loadingLD = MutableLiveData<Boolean>()
    val errorLD = MutableLiveData<Boolean>()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh() {
        loadingLD.value = true
        errorLD.value = false
        launch { // di dalam Coroutine bukan menggunakan .value tapi .postValue
            val db = buildDb(getApplication())
            todoLD.postValue(db.todoDao().selectAll())
            loadingLD.postValue(false)
        }
    }

    fun clearTask(todo: Todo) { // todo mana yang mau didelete
        launch {
            val db = buildDb(getApplication())
            db.todoDao().deleteTodo(todo)
            todoLD.postValue(db.todoDao().selectAll())
        }
    }
}