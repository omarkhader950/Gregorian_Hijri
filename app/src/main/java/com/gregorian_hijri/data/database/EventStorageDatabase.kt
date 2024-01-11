package com.gregorian_hijri.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EventStorageEntity::class], version = 1)
abstract class EventStorageDatabase : RoomDatabase() {
    abstract fun eSDao(): EventStorageDao


    companion object {

        @Volatile
        private var INSTANCE: EventStorageDatabase? = null

        fun getDatabase(context: Context): EventStorageDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }


        private fun buildDatabase(context: Context): EventStorageDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                EventStorageDatabase::class.java,
                "notes_database"
            )
                .build()
        }


    }

}