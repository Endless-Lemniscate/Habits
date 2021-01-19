package com.example.domain.model

import com.example.domain.model.enums.EntityStatus
import java.util.*

data class DoneDate (
    val date: Date,
    var status: EntityStatus
) {
    var id: Int = 0
    var habitId: Int = 0
}