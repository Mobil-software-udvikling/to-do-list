package com.to_do_list.app

import android.os.Parcel
import android.os.Parcelable

data class ToDo(
    val todoId: Int = 0,
    val description: String?,
    val completionState: Int = 0,
    val assignedPeople: String?
)
    :Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(todoId)
        parcel.writeString(description)
        parcel.writeInt(completionState)
        parcel.writeString(assignedPeople)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ToDo> {
        override fun createFromParcel(parcel: Parcel): ToDo {
            return ToDo(parcel)
        }

        override fun newArray(size: Int): Array<ToDo?> {
            return arrayOfNulls(size)
        }
    }

}