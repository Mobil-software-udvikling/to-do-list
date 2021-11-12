package com.to_do_list.app

import com.to_do_list.app.topic.ToDoTopic

object TodoArrayClass {

    val todoTopicArrayList = ArrayList<ToDoTopic>()

    fun addNewTodoTopic(toDoTopic: ToDoTopic){
        todoTopicArrayList.add(toDoTopic)
    }

    fun getTodoTopicList(): ArrayList<ToDoTopic> {
        return todoTopicArrayList
    }

    fun setTodoTopicList(newTodoTopicList: ArrayList<ToDoTopic>) {
        todoTopicArrayList.clear()
        todoTopicArrayList.addAll(newTodoTopicList)
    }
}