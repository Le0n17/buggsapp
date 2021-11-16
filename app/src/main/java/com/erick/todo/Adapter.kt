package com.erick.todo

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class Adapter(val list: MutableList<Task>) :  RecyclerView.Adapter<Adapter.TaskViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.linear_todo_card, parent, false))
    }

    fun add(task: Task) {
        list.add(task)
        notifyItemInserted(list.size -1)
    }

    override fun onBindViewHolder(holder: Adapter.TaskViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount() = list.size

    inner class TaskViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: Task, position: Int) = view.apply {
            val title: TextView = findViewById(R.id.name)
            val dateTime: TextView = findViewById(R.id.datetime)
            val checkBox: CheckBox = findViewById(R.id.ok)

            dateTime.text = data.DateTime.format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm a"))
            title.text = data.title

            checkBox.setOnClickListener {
                list.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, list.size)
            }
        }

    }

}