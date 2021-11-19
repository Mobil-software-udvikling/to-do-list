package com.to_do_list.app

import android.os.Parcel
import android.os.Parcelable

data class ToDoTopic(
    val todoArrayList: ArrayList<ToDo>, var topicName: String, val collaborators: ArrayList<String>
):Parcelable{
    constructor(parcel: Parcel) : this(
        ArrayList<ToDo>(),
        parcel.readString()!!,
        ArrayList<String>()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(topicName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ToDoTopic> {
        override fun createFromParcel(parcel: Parcel): ToDoTopic {
            return ToDoTopic(parcel)
        }

        override fun newArray(size: Int): Array<ToDoTopic?> {
            return arrayOfNulls(size)
        }
    }

}
