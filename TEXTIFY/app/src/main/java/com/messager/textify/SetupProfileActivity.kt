package com.messager.textify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SetupProfileActivity : AppCompatActivity() {

    // data base - posibil sa nu functioneze (FIREBASE nu e facut)
    // min 41-ish
    var binding:SetupProfileActivityBinding? = null
    var auth:FirebaseAuth? = null
    var database:FirebaseDatabase? = null
    var storage:FirebaseStorage? = null
    var selectedImage: Uri? = null
    var dialog:ProgressDialog? = null

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
        binding.continueBtn02.setOnClickListener{
            val name :String = binding.nameBox.text.toString()
            if (name.isEmpty()){
                binding!!.nameBox.setError("Please type a name")
            }
            dialog!!.show()
            if (selectedImage != null){
                val reference = storage.reference.child("Profile")
                        .child(auth.uid!!)

            }

        }

    }
}