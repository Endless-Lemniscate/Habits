package com.example.data.db

import androidx.room.*
import com.example.data.models.RoomHabit
import com.example.data.models.RoomHabitDone
import kotlinx.coroutines.flow.Flow


@Dao
interface HabitDao {
    @Query("Select * from habit order by date desc")
    fun getAllHabits(): Flow<List<RoomHabit>>

    @Query("""SELECT * FROM habit WHERE name LIKE :name order by
                    CASE WHEN :sort = 1 THEN date END ASC,
                    CASE WHEN :sort = 0 THEN date END DESC""")
    fun getAllHabitsWithFilters(name: String, sort: Int): Flow<List<RoomHabit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHabit(roomHabit: RoomHabit)

    @Query("Select * from habit where id like :id")
    suspend fun getHabitById(id: Int): RoomHabit

    @Delete
    fun deleteHabit(roomHabit: RoomHabit)

    @Query("Select * from habit_done where habitId = :id")
    suspend fun getHabitDoneDates(id: Int): List<RoomHabitDone>

    @Insert
    fun insertHabitDone(habitDone: RoomHabitDone)
}