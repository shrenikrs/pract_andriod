package com.wli.test.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.wli.test.R
import com.wli.test.adapter.UserListAdapter
import com.wli.test.data.model.User
import com.wli.test.databinding.MainFragmentBinding
import com.wli.test.ui.activity.DetailsActivity
import com.wli.test.ui.activity.MainActivity
import com.wli.test.utils.Status
import com.wli.test.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var adapter: UserListAdapter
    private lateinit var binding: MainFragmentBinding
    private val viewModel: MainActivityViewModel by viewModels()
    private val mActivity: MainActivity by lazy {
        activity as MainActivity
    }
    val list = arrayListOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {

        adapter = UserListAdapter(requireActivity(), listOf()) {
            startDescriptionActivity(it)
        }
        binding.rvUser.adapter = adapter
        lifecycleScope.launch {
            viewModel._userState.collect{
                when(it.status) {
                    Status.LOADING -> {
                        mActivity.showLoader(mActivity)
                    }
                    Status.SUCCESS -> {
                        mActivity.dismissLoader()
                        if(it.data != null && !it.data.result.isNullOrEmpty()){
                            /*list.add(it.data.result!![0])
                            if(list.size < 10) {
                                Log.d("Result", "=============Size ${list.size}")
                                viewModel.getUser()
                            }*/
                                adapter.mDataList = it.data.result!!
                                adapter.notifyDataSetChanged()

                        }
                    }
                    Status.ERROR -> {
                        mActivity.dismissLoader()
                        (requireActivity() as MainActivity).showSystemDefaultAlertDialog(
                            title = "",
                            message = Status.ERROR,
                            isCancelable = true,
                            callback = null,
                            neutralButton = null,
                            negativeButton = getString(R.string.common_cancel),
                            positiveButton = getString(R.string.common_ok)
                        )
                    }
                }
            }
        }
    }

    private fun startDescriptionActivity(data: User) {
        val intent = Intent(mActivity, DetailsActivity::class.java)
            .putExtra("thumb", data.picture.thumbnail)
            .putExtra("username", data.login.username)
            .putExtra("firstName", data.name.first)
            .putExtra("data", data)
        startActivity(intent)
    }
}
