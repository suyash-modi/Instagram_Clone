package com.droid.instagram.Posts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.droid.instagram.HomeActivity
import com.droid.instagram.Models.Post
import com.droid.instagram.Models.User
import com.droid.instagram.R
import com.droid.instagram.databinding.ActivityPostBinding
import com.droid.instagram.utils.POSTS
import com.droid.instagram.utils.POSTS_FOLDER
import com.droid.instagram.utils.USER_NODE
import com.droid.instagram.utils.USER_PROFILE_FOLDER
import com.droid.instagram.utils.uploadImage
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class PostActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
    var imageUrl: String? = null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->

        uri?.let {

            uploadImage(uri, POSTS_FOLDER) { url ->
                if (url != null) {
                    binding.postImage.setImageURI(uri)
                    imageUrl = url
                }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }

        binding.postImage.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }

        binding.postButton.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                .addOnSuccessListener {
                    var user = it.toObject<User>()

                    var post: Post =
                        Post(
                            postUrl = imageUrl!!,
                            caption = binding.caption.editText?.text.toString(),
                            uid=Firebase.auth.currentUser!!.uid,
                            time = System.currentTimeMillis().toString()
                        )
                    Firebase.firestore.collection(POSTS).document().set(post).addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid.toString())
                            .document().set(post).addOnSuccessListener {
                                startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                                finish()
                            }

                    }
                }

        }
    }
}