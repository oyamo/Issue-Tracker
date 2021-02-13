/*
 * Copyright (c) 2021 Oyamo Brian.
 *
 */

package com.oyasis.issuetracker.kv

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class KVStorage constructor(var applicationContext: Context) {
    private val sharedPrefsFile: String = "KV_STORAGE"
    private val mainKey = MasterKey.Builder(applicationContext)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        applicationContext,
        sharedPrefsFile,
        mainKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun <T>write(ktPair: KTPair<T>, value: T) {
        val editor = sharedPreferences.edit()
        when(ktPair.type) {
            String -> editor.putString(ktPair.key, value as String)
            Boolean -> editor.putBoolean(ktPair.key, value as Boolean)
            Long -> editor.putLong(ktPair.key, value as Long)
            Int -> editor.putInt(ktPair.key, value as Int)
            Float -> editor.putFloat(ktPair.key, value as Float)
        }

        editor.apply()
    }


    fun <T>read(ktPair: KTPair<T>) : Any? {
            return when(ktPair.type) {
                String -> sharedPreferences.getString(ktPair.key, null)
                Boolean -> sharedPreferences.getBoolean(ktPair.key, false)
                Long -> sharedPreferences.getLong(ktPair.key, -1L)
                Int -> sharedPreferences.getInt(ktPair.key, -1)
                Float -> sharedPreferences.getFloat(ktPair.key, -1f)
                else -> null
            }
    }

}