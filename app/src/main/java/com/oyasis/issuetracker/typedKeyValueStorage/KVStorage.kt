/*
 * Copyright (c) 2021 Oyamo Brian.
 *
 */

@file:Suppress("UNCHECKED_CAST")

package com.oyasis.issuetracker.kv

import android.content.Context
import android.content.SharedPreferences


class KVStorage constructor(var applicationContext: Context) {
    private val sharedPrefsFile: String = "KV_STORAGE"
    
    private val sharedPreferences: SharedPreferences = applicationContext.getSharedPreferences(
        sharedPrefsFile,
        Context.MODE_PRIVATE
    )

    fun <T>write(ktPair: KTPair<T>, value: T) {
        val editor = sharedPreferences.edit()
        when(ktPair.type) {
            String::class.java-> editor.putString(ktPair.key, value as String)
            Boolean::class.java -> editor.putBoolean(ktPair.key, value as Boolean)
            Long::class.java -> editor.putLong(ktPair.key, value as Long)
            Int::class.java -> editor.putInt(ktPair.key, value as Int)
            Float::class.java -> editor.putFloat(ktPair.key, value as Float)
        }

        editor.apply()
    }


    fun <T>read(ktPair: KTPair<T>) : T? {
            return when(ktPair.type) {
                String::class.java -> sharedPreferences.getString(ktPair.key, null) as T
                Boolean::class.java -> sharedPreferences.getBoolean(ktPair.key, false) as T
                Long::class.java -> sharedPreferences.getLong(ktPair.key, -1L) as T
                Int::class.java -> sharedPreferences.getInt(ktPair.key, -1)  as T
                Float::class.java -> sharedPreferences.getFloat(ktPair.key, -1f) as T
                else -> null
            }
    }

}