/*
 * Copyright (c) 2021 Oyamo Brian.
 * Last modified on 2/21/21 9:08 PM
 *
 *
 */

package com.oyasis.issuetracker.ui
import android.content.Intent
import android.os.Bundle
import com.oyasis.issuetracker.Constants
import com.oyasis.issuetracker.IssueTrackerActivity
import com.oyasis.issuetracker.databinding.ActivitySplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : IssueTrackerActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        kvStorage.read(ktPair = Constants.isLoggedIn)?.let {
            if(it) {
                GlobalScope.launch (Dispatchers.Main){
                    delay(3000)
                    showHome()
                }
            } else {
                showOnboard()
            }
        }

        showOnboard()
        setContentView(binding.root)
    }


    fun showOnboard() {
        var intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun showHome() {
        var intent = Intent(this@SplashActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}