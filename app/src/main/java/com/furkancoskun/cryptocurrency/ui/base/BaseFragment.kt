package com.furkancoskun.cryptocurrency.ui.base

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.furkancoskun.cryptocurrency.ui.DataStateChangeListener
import com.furkancoskun.cryptocurrency.ui.UICommunicationListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {

    lateinit var dataStateChangeListener: DataStateChangeListener
    lateinit var communicationListener: UICommunicationListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            dataStateChangeListener = context as DataStateChangeListener
        } catch (e: ClassCastException) {
            Log.e("BaseFragment", "$context must implement DataStateChangeListener")
        }

        try {
            communicationListener = context as UICommunicationListener
        } catch (e: ClassCastException) {
            Log.e("BaseFragment", "$context must implement UICommunicationListener")
        }
    }
}