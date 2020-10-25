package com.example.habits.models


data class Habit(
    val name: String,
    val description: String,
    val frequency: Number,
    val period: String,
    val habit_type: Boolean,
    val priority: Number,
    val color: Number
)