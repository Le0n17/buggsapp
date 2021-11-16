package com.erick.todo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
class Task(
    val id: Int,
    val title: String,
    val description: String,
    val DateTime: LocalDateTime
) : Parcelable