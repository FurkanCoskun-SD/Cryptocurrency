package com.furkancoskun.cryptocurrency.ui

import android.app.Dialog
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.furkancoskun.cryptocurrency.R
import com.furkancoskun.cryptocurrency.databinding.ActivityMenuBinding
import com.furkancoskun.cryptocurrency.ext.gone
import com.furkancoskun.cryptocurrency.ext.setupWithNavController
import com.furkancoskun.cryptocurrency.ext.visible
import com.furkancoskun.cryptocurrency.ui.base.BaseActivity
import com.furkancoskun.cryptocurrency.utils.CommonUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuActivity : BaseActivity<ActivityMenuBinding>() {

    private var currentNavController: LiveData<NavController>? = null
    private var loadingDialog: Dialog? = null

    override fun getViewBinding(): ActivityMenuBinding = ActivityMenuBinding.inflate(layoutInflater)

    // - Lifecycle functions - //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup navigation bar
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    // - Navigation bar visibility - //
    override fun hideBottomBar() {
        binding.bottomNav.gone()
    }

    override fun showBottomBar() {
        binding.bottomNav.visible()
    }

    override fun onUIMessageReceivedParamethers(
        uiMessage: UIMessage,
        statusType: Int,
        btnTitle: String?,
        shouldShowCancel: Boolean?
    ) {
    }

    private fun setupBottomNavigationBar() {

        val navGraphIds = listOf(
            R.navigation.coin_markets,
            R.navigation.favorites
        )

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = binding.bottomNav.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        currentNavController = controller
    }

    // - Progress Bar - //
    override fun displayProgressBar(display: Boolean) {
        if (display) {
            showLoading()
        } else {
            hideLoading()
        }
    }

    private fun showLoading() {
        hideLoading()
        loadingDialog = CommonUtils.showLoadingDialog(this)
    }

    private fun hideLoading() {
        loadingDialog?.let { if (it.isShowing) it.cancel() }
    }
}