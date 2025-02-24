package com.example.bluechained.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blocks")
data class BlockEntity(
    @PrimaryKey
    val id: Int = 0,
    val message: String,
    val timeStamp: Long = System.currentTimeMillis(),
    val encryptionStatus: Boolean,
    val hashValue: String
)
