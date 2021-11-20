package com.to_do_list.app.common.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.to_do_list.app.common.entities.ToDo
import com.to_do_list.app.common.entities.ToDoList


/**
 * @author Nichlas Daniel Boraso(nibor19)
 * @author Laust Christensen(lauch19)
 */
@Database(entities = [ToDoList::class, ToDo::class], version = 1)
abstract class ToDoListDatabase : RoomDatabase() {

    abstract fun ToDoListDao(): ToDoListDAO
    abstract fun toDoDao(): ToDoDAO

    companion object {
        private var INSTANCE: ToDoListDatabase? = null

        fun getAppDatabse(context: Context): ToDoListDatabase? {
            if (INSTANCE == null) {
                INSTANCE = databaseBuilder(
                    context.applicationContext,
                    ToDoListDatabase::class.java,
                    "ToDoListDatabase"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }


}