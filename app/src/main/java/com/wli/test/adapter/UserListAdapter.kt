package com.wli.test.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wli.test.data.model.User
import com.wli.test.databinding.RowUserListBinding

class UserListAdapter(
    private val mContext: Context,
    var mDataList: List<User>,
    val itemClickCallback: (data: User) -> Unit
) :
    RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        /**
         * inflate view directly with data binding object and pass it to view holder
         */
        val obj = RowUserListBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return UserViewHolder(obj)
    }

    override fun getItemCount(): Int {
        return if (mDataList.isNullOrEmpty()) 0 else mDataList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(mDataList[holder.adapterPosition], itemClickCallback)
    }

    /**
     * ViewHolder Definition
     */
    class UserViewHolder(private val mBinding: RowUserListBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bind(data: User, itemClickCallback: (data: User) -> Unit) {

            /**
             * Pass data to view through data binding object
             */
            mBinding.data = data
            mBinding.executePendingBindings()

            mBinding.root.setOnClickListener {
                itemClickCallback.invoke(data)
            }
        }

    }
}