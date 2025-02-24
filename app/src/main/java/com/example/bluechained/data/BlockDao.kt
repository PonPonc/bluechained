package com.example.bluechained.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BlockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlock(block: BlockEntity)

    @Query("SELECT * FROM blocks ORDER BY timeStamp ASC")
    fun getAllBlocks(): LiveData<List<BlockEntity>>
}