/*
 * Copyright (c) 2021 Oyamo Brian.
 * Last modified on 2/21/21 9:08 PM
 *
 *
 */

package com.oyasis.issuetracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.oyasis.issuetracker.R
import com.oyasis.issuetracker.databinding.FragmentOnboardBinding
import com.oyasis.issuetracker.databinding.FragmentOnboardSecondBinding
import com.oyasis.issuetracker.databinding.FragmentOnboardThirdBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val POSITION_ARG = "position"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentOnboard.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentOnboard : Fragment() {
    // TODO: Rename and change types of parameters
    private var position: Int? = null
    private lateinit var mView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(POSITION_ARG)

            when(position) {
                0 ->    mView = FragmentOnboardBinding.inflate(layoutInflater).root
                1 ->    mView = FragmentOnboardSecondBinding.inflate(layoutInflater).root
                2 ->    mView = FragmentOnboardThirdBinding.inflate(layoutInflater).root
            }

        }

    }


    override fun onResume() {
        super.onResume()
        activity?.let { fragmentActivity ->
            val onBoardingActivity = fragmentActivity as OnBoardingActivity
            position?.let { it ->
                when(it) {
                   0 ->  onBoardingActivity.binding.onBoardingText.text = resources.getText(R.string.ob_fragment_1)
                   1 ->  onBoardingActivity.binding.onBoardingText.text = resources.getText(R.string.ob_fragment_2)
                   2 -> onBoardingActivity.binding.let { b->
                       b.onBoardingText.text = resources.getText(R.string.ob_fragment_3)
                       b.getStartedbtn.visibility = View.VISIBLE
                   }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return mView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment FragmentOnboard.
         */
        @JvmStatic
        fun newInstance(pos: Int) =
            FragmentOnboard().apply {
                arguments = Bundle().apply {
                    putInt(POSITION_ARG, pos)
                }
            }
    }
}