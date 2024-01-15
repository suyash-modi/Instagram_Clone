package com.droid.instagram.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.droid.instagram.Models.Post
import com.droid.instagram.Models.User
import com.droid.instagram.R
import com.droid.instagram.adapters.FollowRVAdapter
import com.droid.instagram.adapters.PostAdapter
import com.droid.instagram.databinding.FragmentHomeBinding
import com.droid.instagram.utils.FOLLOW
import com.droid.instagram.utils.POSTS
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var postList=ArrayList<Post>()
    private lateinit var adapter: PostAdapter
    private var followList=ArrayList<User>()
    private lateinit var adapter1:FollowRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(layoutInflater, container, false)
        adapter= PostAdapter(requireContext(),postList)
        adapter1 = FollowRVAdapter(requireContext(), followList)
        binding.rv.layoutManager=LinearLayoutManager(requireContext())
        binding.rv.adapter=adapter

        binding.followRv.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.followRv.adapter=adapter1


        setHasOptionsMenu(true)
        ( requireActivity() as AppCompatActivity).setSupportActionBar(binding.materialToolbar2)

        Firebase.firestore.collection(POSTS).get().addOnSuccessListener {
            var tempList=ArrayList<Post>()
            for(i in it.documents)
            {
                var post:Post=i.toObject<Post>()!!
                tempList.add(post)
            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).get().addOnSuccessListener {
            var tempList=ArrayList<User>()
            followList.clear()

            for (i in it.documents)
            {
                var user:User=i.toObject<User>()!!
                tempList.add(user)

            }
            followList.addAll(tempList)
            adapter1.notifyDataSetChanged()
        }

        return binding.root
    }

    companion object {


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}