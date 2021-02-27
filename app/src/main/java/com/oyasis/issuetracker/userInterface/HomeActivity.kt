/*
 * Copyright (c) 2021 Oyamo Brian.
 * Last modified on 2/21/21 9:08 PM
 *
 *
 */

package com.oyasis.issuetracker.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.oyasis.issuetracker.IssueTrackerActivity
import com.oyasis.issuetracker.databinding.ActivityHomeBinding

class HomeActivity : IssueTrackerActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)

        binding.searchButton.setOnClickListener { showSearchFragment() }
        binding.settings.setOnClickListener { openSettingsPage() }
        setContentView(binding.root)
    }


    private fun showSearchFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val searchFragment = SearchFragment()
        searchFragment.show(fragmentTransaction, "searchFragment")
        showKeyBoard()
    }

    private fun showKeyBoard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0)
    }

    private fun openSettingsPage() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }
}