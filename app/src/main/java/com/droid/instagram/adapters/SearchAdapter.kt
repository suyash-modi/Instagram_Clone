package com.droid.instagram.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.droid.instagram.R
import com.droid.instagram.databinding.SearchRvBinding
import com.droid.instagram.utils.FOLLOW
import com.droid.instagram.utils.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class SearchAdapter(var context: Context,var userList: ArrayList<com.droid.instagram.Models.User>)  : RecyclerView.Adapter<SearchAdapter.ViewHolder>(){

    inner class ViewHolder(var binding: SearchRvBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding=SearchRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
     return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var isFollow=false
        Glide.with(context).load(userList.get(position).image).placeholder(R.drawable.user_profile)
            .into(holder.binding.Image)


        holder.binding.name.text=userList.get(position).name

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).whereEqualTo("email",userList.get(position).email).get().addOnSuccessListener {
            if(it.documents.size==0)
            {
                isFollow=false
            }
            else{
                holder.binding.followBtn.text="Unfollow"
                isFollow=true
            }
        }

        holder.binding.followBtn.setOnClickListener {
            if (isFollow)
            {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).whereEqualTo("email",userList.get(position).email).get().addOnSuccessListener {
                   Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).document(it.documents.get(0).id).delete()
                    holder.binding.followBtn.text="Follow"
                    isFollow=false
                }

            }else{

                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).document().set(userList.get(position))
                holder.binding.followBtn.text="Unfollow"
                isFollow=true
            }

        }
    }
}