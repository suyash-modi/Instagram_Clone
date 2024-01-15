package com.droid.instagram

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.droid.instagram.Models.User
import com.droid.instagram.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.statusBarColor= Color.TRANSPARENT
        binding.loginBtn.setOnClickListener {

            if( binding.email.editText?.text.toString().equals("") or
                binding.password.editText?.text.toString().equals(""))
            {
                Toast.makeText(this@LoginActivity, "Please Fill All Details", Toast.LENGTH_SHORT).show()

            }else{

                var user= User(binding.email.editText?.text.toString(),
                    binding.password.editText?.text.toString())

                Firebase.auth.signInWithEmailAndPassword(user.email!!,user.password!!)
                    .addOnCompleteListener{
                        if (it.isSuccessful)
                        {
                            startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
                            finish()

                        }else{
                            Toast.makeText(this@LoginActivity, it.exception?.localizedMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        binding.createBtn.setOnClickListener {
            startActivity(Intent(this@LoginActivity,SignUpActivity::class.java))
            finish()
        }

    }
}