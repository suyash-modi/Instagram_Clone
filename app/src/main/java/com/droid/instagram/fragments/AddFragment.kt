package com.droid.instagram.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.droid.instagram.Posts.PostActivity
import com.droid.instagram.Posts.ReelsActivity
import com.droid.instagram.databinding.FragmentAddBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding=FragmentAddBinding.inflate(inflater, container, false)

        binding.post.setOnClickListener {
           activity?.startActivity(Intent(requireContext(),PostActivity::class.java))
            activity?.finish()

        }
        binding.reel.setOnClickListener {
            activity?.startActivity(Intent(requireContext(),ReelsActivity::class.java))
            activity?.finish()
        }
        return binding.root
    }

    companion object {

    }
}