package com.droid.instagram.Posts

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.droid.instagram.HomeActivity
import com.droid.instagram.Models.Reel
import com.droid.instagram.Models.User
import com.droid.instagram.databinding.ActivityReelsBinding

import com.droid.instagram.utils.REELS
import com.droid.instagram.utils.REELS_FOLDER
import com.droid.instagram.utils.USER_NODE
import com.droid.instagram.utils.uploadVideo
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ReelsActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityReelsBinding.inflate(layoutInflater)
    }
    lateinit var progressDialog: ProgressDialog
    var videoUrl: String? = null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadVideo(uri, REELS_FOLDER, progressDialog) { url ->
                if (url != null) {
                    videoUrl = url
                }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)

        binding.postReel.setOnClickListener {
            launcher.launch("video/*")
        }

        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
            finish()
        }

        binding.postButton.setOnClickListener {

            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                .addOnSuccessListener {
                    var user:User=it.toObject<User>()!!

                    var reel: Reel = Reel(videoUrl!!, binding.caption.editText?.text.toString(),user.image!!)
                    Firebase.firestore.collection(REELS).document().set(reel).addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REELS)
                            .document()
                            .set(reel).addOnSuccessListener {
                                startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
                                finish()
                            }

                    }
                }
        }
    }
}