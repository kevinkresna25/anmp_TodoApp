package com.ubaya.todoapp.model

import DB_NAME
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile // menginfokan ke semua thread database sudah tidak null lagi
        private var instance: TodoDatabase ?= null

        private val LOCK = Any()

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                TodoDatabase::class.java, DB_NAME).build() // nama database = "tododb"
    }

    operator fun invoke(context: Context) {
        if(instance == null) { // kalau database nya belum ada
            synchronized(LOCK) { // akan lock thread
                instance ?: buildDatabase(context).also {
                    instance = it // object database di simpan dalam bentuk object
                }
            }
        }
    }
}