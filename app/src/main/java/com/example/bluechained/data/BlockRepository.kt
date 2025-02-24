package com.example.bluechained.data

import android.content.Context
import androidx.lifecycle.LiveData


class BlockRepository(context: Context) {
    private val blockDao = BlueChainedDatabase.getDatabase(context).blockDao()

    val allBlocks: LiveData<List<BlockEntity>> = blockDao.getAllBlocks()

    suspend fun insert(block: BlockEntity){
        blockDao.insertBlock(block)
    }
}