package com.ubaya.todoapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg todo:Todo)

    @Query("SELECT * FROM todo") // nama table "todo" diperoleh dari Model.kt `data class "Todo"` pasti huruf kecil
    fun selectAll(): List<Todo> // Output

    @Query("SELECT * FROM todo WHERE uuid = :id") // ":id" dapat dari `selectTodo(id:Int)`
    fun selectTodo(id:Int): Todo

    @Delete
    fun deleteTodo(todo:Todo)
}