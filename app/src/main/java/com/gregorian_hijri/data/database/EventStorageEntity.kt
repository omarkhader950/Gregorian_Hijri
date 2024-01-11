package com.gregorian_hijri.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_storage")
class EventStorageEntity (
    @PrimaryKey(autoGenerate = false)
    val ID: String,
    var event_name: String,
    var event_description: String,
    var Gregorian_date: String,
    var Hijri_date: String,
    var server_datetime: String,
)
