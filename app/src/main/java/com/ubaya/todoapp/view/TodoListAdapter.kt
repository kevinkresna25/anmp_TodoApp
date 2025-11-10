package com.ubaya.todoapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.todoapp.databinding.TodoItemLayoutBinding
import com.ubaya.todoapp.model.Todo

class TodoListAdapter(val todoList:ArrayList<Todo>, val listener: TodoItemListener)
    :RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {
    class TodoViewHolder(var binding: TodoItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        var binding = TodoItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.binding.checkTask.text = todoList[position].title
        holder.binding.checkTask.setOnCheckedChangeListener { btn, isChecked ->
            if(btn.isPressed && isChecked) {
                listener.onTodoCheck(todoList[position])
            }
        }
        holder.binding.checkTask.isChecked = false
    }

    override fun getItemCount() = todoList.size

    fun updateTodoList(newTodoList: List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        // untuk merender ulang data dari recView nya
        notifyDataSetChanged()
    }
}
