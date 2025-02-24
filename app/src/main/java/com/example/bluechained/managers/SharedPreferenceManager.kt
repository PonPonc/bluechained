package com.example.bluechained.managers

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_DATA, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun setPowValue(powValue: Int) {
        editor.putInt(PROOF_OF_WORK, powValue).apply()
    }

    fun getPowValue(): Int {
        return sharedPreferences.getInt(PROOF_OF_WORK, DEFAULT_PROOF_OF_WORK)
    }

    fun setEncryptionStatus(isActivated: Boolean) {
        editor.putBoolean(ENCRYPTION_STATUS, isActivated).apply()
    }

    fun getEncryptionStatus(): Boolean {
        return sharedPreferences.getBoolean(ENCRYPTION_STATUS, false)
    }

    companion object {
        private const val PREFERENCE_DATA = "com.example.bluechained"
        private const val ENCRYPTION_STATUS = "encryption_status"
        private const val PROOF_OF_WORK = "proof_of_work"
        private const val DEFAULT_PROOF_OF_WORK = 2
    }
}