package com.example.data.db

import androidx.room.*
import com.example.data.models.RoomDoneDate
import com.example.data.models.RoomHabit
import com.example.data.models.RoomHabitUnited
import kotlinx.coroutines.flow.Flow


@Dao
interface HabitDao {

    @Transaction
    @Query("Select * from habit where status != 0")
    fun getNotSyncedAndDeleted(): Flow<List<RoomHabitUnited>>

    @Transaction
    @Query(
        """SELECT * FROM habit WHERE status != 2 and name LIKE :name order by
                    CASE WHEN :sort = 1 THEN date END ASC,
                    CASE WHEN :sort = 0 THEN date END DESC"""
    )
    fun getAllHabitsWithFilters(name: String, sort: Int): Flow<List<RoomHabitUnited>>

    @Query("Select * from habit where status = 2")
    fun getDeletedHabits(): Flow<List<RoomHabit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(roomHabit: RoomHabit)

    @Query("Select * from habit where id like :id")
    suspend fun getHabitById(id: Int): RoomHabitUnited

    @Delete
    suspend fun deleteHabit(roomHabit: RoomHabit)

    @Query("UPDATE habit SET status = 0 WHERE id = :id")
    suspend fun setHabitSynced(id: Int)

    @Query("UPDATE habit SET remoteId = :remote_id, status = 0 WHERE id = :id")
    suspend fun setRemoteId(id: Int, remote_id: String)

    @Query("UPDATE habit SET status = 2 WHERE id = :id")
    suspend fun syncedDeleteHabit(id: Int)

    @Query("Select * from habit")
    suspend fun getAll(): List<RoomHabit>

    @Query("UPDATE done_dates SET status = 0 WHERE id = :id")
    suspend fun setDateSynced(id: Int)

    @Insert
    suspend fun insertDoneDate(roomDoneDate: RoomDoneDate)
}