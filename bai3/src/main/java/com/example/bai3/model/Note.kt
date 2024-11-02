package com.example.bai3.model
import java.util.*

data class Note(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: Date,
    val isImportant: Boolean
)
