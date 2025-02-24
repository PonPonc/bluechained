package com.example.bluechained.utils

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec

class CipherUtils {
    private val PASSWORD: String = "C00l K1dz! @ p4rK"
    private val ALGORITHM: String = "DES"

    fun encryptIt(value: String): String {
        return try {
            val keySpec = DESKeySpec(PASSWORD.toByteArray(Charsets.UTF_8))
            val keyFactory = SecretKeyFactory.getInstance(ALGORITHM)
            val key = keyFactory.generateSecret(keySpec)
            val clearText = value.toByteArray(Charsets.UTF_8)
            val cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.ENCRYPT_MODE, key)
            Base64.encodeToString(cipher.doFinal(clearText), Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            value
        }
    }

    fun decryptIt(value: String): String{
        return try{
            val keySpec = DESKeySpec(PASSWORD.toByteArray(Charsets.UTF_8))
            val keyFactory = SecretKeyFactory.getInstance(ALGORITHM)
            val key = keyFactory.generateSecret(keySpec)
            val encryptedPwdBytes = Base64.decode(value, Base64.DEFAULT)
            val cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.DECRYPT_MODE, key)
            val decryptedValueBytes = (cipher.doFinal(encryptedPwdBytes))
            return decryptedValueBytes.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            value
        }
    }
}