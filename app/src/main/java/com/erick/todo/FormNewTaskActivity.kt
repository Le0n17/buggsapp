package com.erick.todo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.erick.todo.MainActivity.Companion.NEW_TASK
import com.erick.todo.MainActivity.Companion.NEW_TASK_KEY
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class FormNewTaskActivity : AppCompatActivity() {

    private lateinit var edtTitle: EditText
    private lateinit var edtDesc: EditText
    private lateinit var edtDate: EditText
    private lateinit var edtTime: EditText
    private lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_new_task)

        initViews()
    }

    private fun initViews() {
        edtTitle = findViewById(R.id.edtTitle)
        edtDesc = findViewById(R.id.edtDesc)
        edtDate = findViewById(R.id.edtDate)
        edtTime = findViewById(R.id.edtTime)
        btn = findViewById(R.id.btn)

        btn.setOnClickListener {
            if (edtTitle.text.toString() == "" || edtTime.text.toString() == "" || edtDesc.text.toString() == "" || edtDate.text.toString() == "")
                Toast.makeText(this, "No se puede crear la tarea", Toast.LENGTH_SHORT).show()
            else {
                setResult(
                    NEW_TASK, Intent().putExtra(
                        NEW_TASK_KEY,
                        Task(
                            0,
                            edtTitle.text.toString(),
                            edtDesc.text.toString(),
                            LocalDateTime.of(
                                LocalDate.parse(
                                    edtDate.text,
                                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
                                ),
                                LocalTime.parse(edtTime.text, DateTimeFormatter.ofPattern("HH:mm"))
                            )
                        )
                    )
                )
                finish()
            }
        }

        edtDate.setOnClickListener {
            val nowDate = LocalDate.now()
            val datePicker = DatePickerDialog(
                this,
                { _, anio, mes, dia ->
                    edtDate.setText("$dia/$mes/$anio")
                },
                nowDate.year,
                (nowDate.monthValue - 1),
                nowDate.dayOfMonth
            )
            datePicker.datePicker.minDate = System.currentTimeMillis() - 10000
            datePicker.show()
        }

        edtTime.setOnClickListener {
            val nowTime = LocalTime.now()
            TimePickerDialog(
                this,
                { _, hora, minut0 ->
                    val m = if (minut0 < 10) "0$minut0" else minut0
                    val h = if (hora < 10) "0$hora" else hora
                    edtTime.setText("$h:$m")
                },
                nowTime.hour,
                nowTime.minute,
                true
            ).show()
        }
    }
}