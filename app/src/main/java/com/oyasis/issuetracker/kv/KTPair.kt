/*
 * Copyright (c) 2021 Oyamo Brian.
 *
 */

package com.oyasis.issuetracker.kv


class KTPair <T>constructor(private val k :String, private val t: T ) {

    val key = k
    val type = t

    init {

        // Throw an error if the type specified cannot be Serialised by android.content.SharedPreferences
        if(t !is Boolean && t !is Long && t !is Int && t !is Float && t !is String )
            throw IllegalTypeError("Type in the constructor cannot be stored")
    }

}