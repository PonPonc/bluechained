package com.example.bluechained.models

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

fun calculateHashDetail(blockModel: BlockModel): String {
    return try {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val txt = blockModel.str()
        val bytes = messageDigest.digest(txt.toByteArray())
        val builder = StringBuilder()
        for (b in bytes) {
            val hex = String.format("%02x", b)
            builder.append(hex)
        }
        builder.toString()
    } catch (e: NoSuchAlgorithmException) {
        ""
    }
}

class BlockModel(
    private var index: Int,
    private var timestamp: Long,
    private var previousHash: String,
    private var data: String,
    private var hash: String = calculateHashDetail(BlockModel(index, timestamp, previousHash, data, nonce = 0)),
    private var nonce: Int
) {
    fun str(): String {
        return "$index$timestamp$previousHash$data$nonce"
    }

    fun mineBlock(difficulty: Int){
        nonce = 0
        while (!previousHash.substring(0, difficulty).equals(addZeros(difficulty))){
            nonce++
            hash = calculateHashDetail(blockModel = this)
        }
    }

    private fun addZeros(difficulty: Int): String{
        var builder = StringBuilder()
        for (i in 0 until difficulty){
            builder.append("0")
        }
        return builder.toString()
    }
}