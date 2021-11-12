package com.to_do_list.app

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class ListOfToDoAdapter(private var toDo: List<ToDo>) :
    RecyclerView.Adapter<ListOfToDoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val description: TextView = itemView.findViewById(R.id.et_descriptionTODO)
        //val completionState: TextView = itemView.findViewById(R.id.et_completionstateTODO)
        //val people: TextView = itemView.findViewById(R.id.et_peopleTODO)

        init {
            itemView.setOnClickListener{
                val postion: Int = adapterPosition
                Toast.makeText(itemView.context, "You clicked on item # ${postion + 1})", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOfToDoAdapter.ViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.todo_layout, parent, false)
        return  ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return toDo.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.description.text = toDo[position].toString()
    }

}