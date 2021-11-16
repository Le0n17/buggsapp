package com.erick.todo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDateTime
import java.util.ArrayList

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {

    private lateinit var rcv: RecyclerView
    private lateinit var btn: FloatingActionButton
    private lateinit var adapter: Adapter
    private var tasks = mutableListOf(
        Task(0, "Task 0", "Description 0", LocalDateTime.now()),
        Task(1, "Task 1", "Description 1", LocalDateTime.now()),
        Task(2, "Task 2", "Description 2", LocalDateTime.now())
    )

    companion object {
        val NEW_TASK = 200
        val NEW_TASK_KEY = "newtask"
        val TASKS = "tasks"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState?.let {
            val savedTasks = it.getParcelableArrayList<Task>(TASKS)?.toMutableList() ?: tasks
            tasks = savedTasks
        }

        initViews()
        setAdapter()
    }

    private fun initViews() {
        btn = findViewById(R.id.btn)
        rcv = findViewById(R.id.rcv)


        btn.setOnClickListener {
            startActivityForResult(Intent(this, FormNewTaskActivity::class.java), NEW_TASK)
        }
    }

    private fun setAdapter() {
        adapter = Adapter(tasks)
        rcv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcv.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.apply {
            putParcelableArrayList(TASKS, tasks as ArrayList<Task>)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ( requestCode == NEW_TASK ){
            data?.getParcelableExtra<Task>(NEW_TASK_KEY)?.let {
                adapter.add(it)
            }
        }
    }
}