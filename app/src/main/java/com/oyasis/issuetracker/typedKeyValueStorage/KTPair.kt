/*
 * Copyright (c) 2021 Oyamo Brian.
 *
 */

package com.oyasis.issuetracker.kv


class KTPair<T>(private val k: String, private val t: Class<T>) {

    val key = k
    val type = t

    init {

        // Throw an error if the type specified cannot be Serialised by android.content.SharedPreferences
        if (!arrayListOf<Class<*>>(
                Int::class.java,
                Boolean::class.java,
                Long::class.java,
                Long::class.java,
                Float::class.java,
                String::class.java,
                Float::class.java
            ).contains(t)
        )
            throw IllegalTypeError("Type in the constructor cannot be stored")
    }

}