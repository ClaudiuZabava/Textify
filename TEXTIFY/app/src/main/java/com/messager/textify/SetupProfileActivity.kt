package com.messager.textify

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.messager.textify.databinding.ActivitySetupProfileBinding

class SetupProfileActivity : AppCompatActivity() {

    // data base - posibil sa nu functioneze (FIREBASE nu e facut)
    // min 41-ish
    var binding:ActivitySetupProfileBinding? = null
    var auth: FirebaseAuth? = null
    var database: FirebaseDatabase? = null
    var storage: FirebaseStorage? = null
    var selectedImage: Uri? = null
    var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetupProfileBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        dialog!!.setMessage("Updating Profile...")
        dialog!!.setCancelable(false)
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()
        supportActionBar?.hide()
        binding!!.imageView.setOnClickListener{
            var intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent,45)
        }
        binding!!.continueButton2.setOnClickListener{
            val name :String = binding!!.nameBox.text.toString()
            if (name.isEmpty()){
                binding!!.nameBox.error = "Please type a name"
            }
            dialog!!.show()
            if (selectedImage != null){
                val reference = storage!!.reference.child("Profile")
                        .child(auth!!.uid!!)

            }

        }

    }
}