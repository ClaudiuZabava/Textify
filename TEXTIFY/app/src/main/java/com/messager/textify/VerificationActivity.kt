package com.messager.textify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.messager.textify.databinding.ActivityVerificationBinding


class VerificationActivity : AppCompatActivity() {

    var binding: ActivityVerificationBinding? = null
    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        auth = FirebaseAuth.getInstance()
        if(auth!!.currentUser!=null)
        {
            val intent = Intent(this@VerificationActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        supportActionBar!!.hide()
        binding!!.editNumber.requestFocus()
        binding!!.continueButton.setOnClickListener {
            val intent = Intent(this@VerificationActivity, OTPActivity::class.java)
            intent.putExtra("phoneID", binding!!.editNumber.text.toString())
            startActivity(intent)


        }
    }
}