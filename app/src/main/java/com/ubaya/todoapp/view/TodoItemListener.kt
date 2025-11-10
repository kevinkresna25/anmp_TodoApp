package com.ubaya.todoapp.view

import com.ubaya.todoapp.model.Todo

// di interface ini tidak boleh ada body di func nya
// hanya mendekalarasi func

interface TodoItemListener {
    fun onTodoCheck(todo: Todo)
}