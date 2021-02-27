/*
 * Copyright (c) 2021 Oyamo Brian.
 * Last modified on 2/21/21 9:08 PM
 *
 *
 */

package com.oyasis.issuetracker.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.oyasis.issuetracker.ITFragmentActivity
import com.oyasis.issuetracker.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : ITFragmentActivity() {
    // View binding
    private val NUM_PAGES = 3
    lateinit var binding: ActivityOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)

        // Set the Adapter
        val pageAdapter = OnBoardPageAdapter(this)

        // Set up the ViewPager with the TabLayout
        binding.let {
            it.onboardVP.adapter = pageAdapter
            it.getStartedbtn.setOnClickListener { openLoginPage() }
            val mediator = TabLayoutMediator(it.scrollTab, it.onboardVP, true) { _, _ -> run {} }
            mediator.attach()
        }
        setContentView(binding.root)
    }

    fun openLoginPage() {
        var intent = Intent(this@OnBoardingActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private inner class OnBoardPageAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES
        override fun createFragment(position: Int): Fragment {

          return FragmentOnboard.newInstance(position)
        }
    }
}