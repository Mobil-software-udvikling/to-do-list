package com.to_do_list.app.common.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "ToDo", foreignKeys = arrayOf(
        ForeignKey(
            entity = ToDoList::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("list"),
            onDelete = ForeignKey.CASCADE
        )
    )
)
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val description: String,
    val completionState: Int = 0,
    val assignedPeople: String,
    val list: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(description)
        parcel.writeInt(completionState)
        parcel.writeString(assignedPeople)
        parcel.writeInt(list)
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
