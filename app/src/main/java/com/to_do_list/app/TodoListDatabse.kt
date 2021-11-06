package com.to_do_list.app

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Room.databaseBuilder
import androidx.room.Database


@Database(entities = [ToDoList::class, ToDo::class], version = 1)
abstract class TodoListDatabse : RoomDatabase() {

    abstract fun ToDoListDao() : ToDoListDAO
    abstract fun toDoDao() : ToDoDAO

    companion object {
       private var INSTANCE : TodoListDatabse? = null

       fun getAppDatabse(context : Context) : TodoListDatabse? {
           if(INSTANCE == null){
               INSTANCE = databaseBuilder(context.applicationContext, TodoListDatabse::class.java, "ToDoListDatabase")
                   .allowMainThreadQueries()
                   .build()
           }
           return INSTANCE
       }
    }


}