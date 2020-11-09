package com.example.domain.enums

import java.time.Duration


enum class HabitPeriod(val duration: Duration, val string: String) {
    HOUR(Duration.ofHours(1), "Час"),
    THREE_HOURS(Duration.ofHours(3), "3 часа"),
    SIX_HOURS(Duration.ofHours(6), "6 часов"),
    TWELVE_HOURS(Duration.ofHours(12), "12 часов"),
    DAY(Duration.ofDays(1), "День"),
    THREE_DAYS(Duration.ofDays(3), "3 дня"),
    WEEK(Duration.ofDays(7), "Неделя"),
    TWO_WEEKS(Duration.ofDays(14), "2 недели"),
    MONTH(Duration.ofDays(30), "Месяц")
}