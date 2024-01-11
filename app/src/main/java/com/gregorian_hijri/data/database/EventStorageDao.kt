package com.gregorian_hijri.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EventStorageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEvent(event: EventStorageEntity)

    @Query("select * from event_storage")
    fun readAllEvents(): LiveData<List<EventStorageEntity>>

    @Query("select * from event_storage where ID=:id")
    fun selectEvent(id: String): EventStorageEntity

    @Query("delete from event_storage where ID=:id")
    fun deleteEvent(id: String)
}