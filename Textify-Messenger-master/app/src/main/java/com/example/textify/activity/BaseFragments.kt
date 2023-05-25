package com.example.textify.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.textify.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

open class BaseFragments : Fragment() {

    private lateinit var docRef: DocumentReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database: FirebaseFirestore = FirebaseFirestore.getInstance()
        docRef = database.collection(Constants.KEY_COLLECTION_USER).document(FirebaseAuth.getInstance().uid!!)
    }

    override fun onResume() {
        super.onResume()
        Glide.with(this).resumeRequests()
        docRef.update("online_status",true)
    }

}