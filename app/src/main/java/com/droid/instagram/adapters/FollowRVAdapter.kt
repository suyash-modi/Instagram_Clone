package com.droid.instagram.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.droid.instagram.databinding.FollowRvBinding
import com.droid.instagram.Models.User
import com.droid.instagram.R

class FollowRVAdapter(var context: Context, var followList: ArrayList<User>): RecyclerView.Adapter<FollowRVAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: FollowRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding=FollowRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return followList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(followList.get(position).image).placeholder(R.drawable.user_profile).into(holder.binding.image)
        holder.binding.name.text=followList.get(position).name
    }
}