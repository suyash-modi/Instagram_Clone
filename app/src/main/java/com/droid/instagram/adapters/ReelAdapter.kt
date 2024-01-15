package com.droid.instagram.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import com.droid.instagram.Models.Reel
import com.droid.instagram.R
import com.droid.instagram.databinding.ReelDgBinding
import com.squareup.picasso.Picasso

class ReelAdapter (var context: Context,var reelList: ArrayList<Reel>): RecyclerView.Adapter<ReelAdapter.ViewHolder>() {

    inner class ViewHolder(var binding:ReelDgBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding= ReelDgBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(reelList.get(position).profileLink).placeholder(R.drawable.user_profile).into(holder.binding.profilee)
        Glide.with(context)
            .load(reelList.get(position).profileLink)
            .placeholder(R.drawable.user_profile)
            .into(holder.binding.profilee);
        holder.binding.caption.setText(reelList.get(position).caption)
        holder.binding.videoView.setVideoPath(reelList.get(position).reelUrl)
        holder.binding.videoView.setOnPreparedListener{
            holder.binding.progresBar.visibility= View.GONE
            holder.binding.videoView.start()
        }
    }
}