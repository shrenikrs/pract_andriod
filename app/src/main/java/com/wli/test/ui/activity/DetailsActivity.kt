package com.wli.test.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.wli.test.R
import com.wli.test.data.model.User
import com.wli.test.databinding.ActivityDetailsBinding
import com.wli.test.databinding.ActivityMainBinding
import com.wli.test.ui.fragment.MainFragment
import com.wli.test.utils.loadImageFromGlide
import com.wli.test.utils.loadImageFromServer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : BaseActivity<ActivityDetailsBinding>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView(R.layout.activity_details)

        init()
    }

    /**
     * All operations, initialization, data extraction and setting up listeners will starts from here.
     * Purpose is only if there will be a need of redo whole thing than only one method call serves the need.
     */
    private fun init() {
        supportActionBar?.title = "Profile Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val data = intent.getSerializableExtra("data") as User
        mBinding.data = data
        mBinding.executePendingBindings()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }


}
