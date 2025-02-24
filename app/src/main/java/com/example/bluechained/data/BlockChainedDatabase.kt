package com.example.bluechained.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BlockEntity::class], version=1, exportSchema = false)
abstract class BlueChainedDatabase : RoomDatabase(){
    abstract fun blockDao(): BlockDao

    companion object{
        @Volatile
        private var INSTANCE: BlueChainedDatabase? = null

        fun getDatabase(context: Context): BlueChainedDatabase {
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BlueChainedDatabase::class.java,
                    "bluechained_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}