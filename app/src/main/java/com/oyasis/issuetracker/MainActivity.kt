/*
 * Copyright (c) 2021 Oyamo Brian.
 *
 */

package com.oyasis.issuetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : IssueTrackerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}